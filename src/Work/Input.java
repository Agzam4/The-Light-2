package Work;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class Input {

		private static String DATE = Shift(GetDate(), 10); 
		private static String ACTIVATION_FULL_VERSION_CODE = DATE; 
		public boolean IS_FULL_VERSION = false; 
		
		public boolean IsFV() {
			File_ file = new File_();
			try {
				DATE = file.ReadFile("src\\data\\date.data");
				ACTIVATION_FULL_VERSION_CODE = file.ReadFile("src\\data\\ac.data");//Shift(DATE, 1);
//				System.out.println("AFVC: " + Shift(ACTIVATION_FULL_VERSION_CODE, 1));
//				System.out.println("DATE: " + DATE);
				IS_FULL_VERSION = Shift(ACTIVATION_FULL_VERSION_CODE, 1).equals(DATE);
			} catch (IOException e) {
				try {
					file.WriteFile("src\\data\\date.data", DATE);
					file.WriteFile("src\\data\\ac.data", "");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Err\n" + e1.getMessage());
				}
			}
			return IS_FULL_VERSION;
		}
		
		public String getCode() {
			IsFV();
			return Shift(DATE, -1);
		}
		
//		public static void main(String[] args) {
//			//IsFV();
//			System.out.println(Shift(ACTIVATION_FULL_VERSION_CODE, -1));
//		}
		
		public void ActivateCode() {
			File_ file = new File_();
			try {
				String code = JOptionPane.showInputDialog("AFVC:" + DATE + "\n Here write you code:");
				file.WriteFile("src\\data\\ac.data", code);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Err\n" + e.getMessage());
			}
			IsFV();
			if(IS_FULL_VERSION) {
				JOptionPane.showMessageDialog(null, "FULL VERSION");
			}
		}

		private static String GetDate() {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		    Date date = new Date();
			return dateFormat.format(date);
		}

		public static String Shift(String code, int value) {
			char[] cs = code.toCharArray();
			String returned = "";
			for (int i = 0; i < cs.length; i++) {
				returned += (char)((int)(cs[i])+value);
			}
			return returned;
		}
		
		
}
