
import java.awt.Color;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class WindowsButtons {

	//private boolean onButton;
	//private boolean click;
	//private Color backgroundC;
	//private Color backgroundNC;
	//private Color backgroundBP;
	
	public void ConvertToWindowsButton(JComponent button, Color backgroundThenOnButton, Color backgroundThen_No_OnButton,  Color backgroundThenPress) {
		//backgroundC = backgroundThenOnButton;
		//backgroundNC = backgroundThen_No_OnButton;
		//backgroundBP = backgroundThenPress;
		
		if(backgroundThen_No_OnButton == null) {
			backgroundThen_No_OnButton = button.getBackground();
		}

		if(backgroundThenOnButton == null) {
			backgroundThenOnButton = button.getBackground();
		}
		
		if(backgroundThenPress == null) {
			backgroundThenPress = new Color(225,225,225);
		}
		Conver(button, backgroundThenOnButton, backgroundThen_No_OnButton, backgroundThenPress);
	}
	
	
	private void Conver(JComponent button, Color backgroundC, Color backgroundNC, Color backgroundBP) {
		
		try {
			//((AbstractButton) button).setMargin(new Insets(3, 2, 2, 2));
			((AbstractButton) button).setBorderPainted(false);
			button.setFocusable(false);
		} catch (Exception e) {
		}
		
		Thread Button = new Thread() {
			@Override
			public void run() {
				while (true) {
					if(((AbstractButton) button).getModel().isRollover()) {//if (onButton) {
						//Set_Color(button, backgroundC, click, backgroundBP);
						Set_Color(button, backgroundC, ((AbstractButton) button).getModel().isPressed(), backgroundBP);
					} else {
						Set_Color(button, backgroundNC, false, backgroundBP);
					}

					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Button.start();
		
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//UIManager.getSystemLookAndFeelClassName());
			//System.out.println(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	private void Set_Color(JComponent c, Color newColor, boolean Click, Color backgroundBP) {
		Color color = c.getBackground();
		int r = newColor.getRed();
		int g = newColor.getGreen();
		int b = newColor.getBlue();
		if(Click) {
			r = backgroundBP.getRed();
			g = backgroundBP.getGreen();
			b = backgroundBP.getBlue();
			c.setBackground(new Color(
					(int) Math.round(((color.getRed() - r) / 1.5) + r),
					(int) Math.round(((color.getBlue() - g) / 1.5) + g),
					(int) Math.round(((color.getGreen() - b) / 1.5) + b))
					);
		}else {
			c.setBackground(new Color(
					(int) Math.floor(((color.getRed() - r) / 2) + r),
					(int) Math.floor(((color.getBlue() - g) / 2) + g),
					(int) Math.floor(((color.getGreen() - b) / 2) + b))
					);
		}
		

	}
}
