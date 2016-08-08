import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import jreframeworker.annotations.fields.DefineField;
import jreframeworker.annotations.methods.DefineMethod;
import jreframeworker.annotations.methods.MergeMethod;
import jreframeworker.annotations.types.MergeType;

@MergeType
public class GlgSCADAViewerMods extends GlgSCADAViewer {

	private static final long serialVersionUID = 1L;

	public GlgSCADAViewerMods(String[] arg) {
		super(arg);
	}
	
	@Override
	@MergeMethod
	public void ProcessArgs(String [] arg){
		System.out.println("GATEKEEPER-CLIENT: Sending to profile to server...");
		String response = excutePost("http://direct.matthewschlue.com:5001/auth", getProfile());
		
		if(response != null && response.contains("verified to start receiving data.")){
			System.out.println("GATEKEEPER-CLIENT: Gatekeeper recognized client!");
			super.ProcessArgs(arg);
		} else {
			System.out.println("GATEKEEPER-CLIENT: Gatekeeper did not recognize client, two factor authentication "
					+ "must be completed for Gatekeeper to allow incoming client requests.");
			JPanel panel = new JPanel();
			JLabel usernameLabel = new JLabel("Enter your two factor authentication token:");
			panel.add(usernameLabel);
			JTextField tokenField = new JTextField(10);
			panel.add(tokenField);

			String[] options = new String[] {"Authenticate", "Quit"};
			
			int option = JOptionPane.showOptionDialog(null, panel, "Device Authentication Challenge", JOptionPane.NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
			
			if (option == 0) {
				String token = tokenField.getText();
				System.out.println("GATEKEEPER-CLIENT: Sending to token response to server...");
				response = executeGet("http://direct.matthewschlue.com:5001/2fa?token=" + token);
				if(response != null && response.contains("Two Factor successfully completed!")){
					System.out.println("GATEKEEPER-CLIENT: Gatekeeper accepted token.");
					super.ProcessArgs(arg);
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
						    "Gatekeeper rejected your token.",
						    "Invalid Token",
						    JOptionPane.ERROR_MESSAGE);
					System.out.println("GATEKEEPER-CLIENT: Gatekeeper rejected token.");
					System.exit(-1); // unauthenticated and unrecognized session!
				}
			} else {
				System.exit(-1); // unauthenticated and unrecognized session!
			}
		}
	}
	
	@DefineField
	private boolean knowledgeChallengePassed = false;
	
	@Override
	@MergeMethod
	public void DisplayAlarmDialog() {
		if(knowledgeChallengePassed){
			super.DisplayAlarmDialog();
		} else {
			JPanel panel = new JPanel();
			JLabel usernameLabel = new JLabel("Username:");
			panel.add(usernameLabel);
			JTextField usernameField = new JTextField(15);
			panel.add(usernameField);
			
			JLabel passwordLabel = new JLabel("Password:");
			panel.add(passwordLabel);
			JPasswordField passwordField = new JPasswordField(15);
			panel.add(passwordField);
			String[] options = new String[] {"Login", "Cancel"};
			
			int option = JOptionPane.showOptionDialog(null, panel, "Knowledge Authentication Challenge", JOptionPane.NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
			
			// pressed login button
			if (option == 0) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				System.out.println("GATEKEEPER-CLIENT: Sending challenge response to Gatekeeper...");
				
				String response = executeGet("http://direct.matthewschlue.com:5001/login?username=" + username + "&password=" + password);
				if(response != null && response.contains("Login Succeeded!")){
					knowledgeChallengePassed = true;
					super.DisplayAlarmDialog();
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
						    "Gatekeeper rejected your credentials.",
						    "Invalid Username or Password",
						    JOptionPane.ERROR_MESSAGE);
					System.out.println("GATEKEEPER-CLIENT: Gatekeeper rejected credentials.");
				}
			}
		}
	}
	
	@DefineMethod
	public String excutePost(String targetURL, String json) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setRequestProperty("Content-Length", "" + Integer.toString(json.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(json);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	@DefineMethod
	private String executeGet(String targetURL) {
		try {
			StringBuilder result = new StringBuilder();
			URL url = new URL(targetURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			return result.toString();
		} catch (Exception e){
			return null;
		}
	}
	
	@DefineMethod
	private String getProfile() {
		String result = "{";

		// hostname
		try {
			String hostname = InetAddress.getLocalHost().getHostName();
			result += "\"hostname\": \"" + hostname + "\",";
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		// network interfaces
		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface n = e.nextElement();
				if (n.getName().equals("en0")) {
					byte[] mac = n.getHardwareAddress();
					String macString = "";
					for (int i = 0; i < mac.length; i++) {
						macString += String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : "");
					}
					result += "\"mac\": \"" + macString + "\",";
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// os
		result += "\"os\": \"" + System.getProperty("os.name") + "\",";

		// username
		result += "\"username\": \"" + System.getProperty("user.name") + "\",";

		// java version
		result += "\"java_version\": \"" + System.getProperty("java.runtime.version") + "\"";

		result += "}";
		return result;
	}

}
