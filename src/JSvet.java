import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Work.File_;
import Work.Input;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;

public class JSvet extends JFrame {

	private static final long serialVersionUID = 1L;
	
	Input input = new Input();
	//JOptionPane.showInputDialog("Code:");
	private boolean isFullVersion = false;
	private JPanel contentPane;
	private JLabel itemId;
	private final String title = "The Light 2 - by Agzam4 - ";
	private String saveUrl = "";
	
	int h = 50;
	int w = 50;
	
	boolean edit = false;

	private int size = 10;
	private BufferedImage img = new BufferedImage(h*size, w*size, BufferedImage.TYPE_INT_RGB);
	
	private int[][] lightR = new int[h][w];
	private int[][] lightG = new int[h][w];
	private int[][] lightB = new int[h][w];
	private int[][] type = new int[h][w];
	private int[][] opend = new int[h][w];
	
	// TODO: id

	private final boolean[] isWires = new boolean[] {
			false,false,false,false,false, // 0-4
			false,false,false,false,false, // 5-9
			false,false,false,false,false, // 10-14
			false,false,false,false,true,  // 15-19
			true,false,false,false,false,  // 20-24
			false,false,false,false,false
			};
	private final boolean[] isWalls = new boolean[] {
			false,true,false,false,false,  // 0-4
			false,false,false,false,false, // 5-9
			false,false,false,false,false, // 10-14
			false,true,true,true,true, // 15-19
			false,false,false,false,false  // 20-24
			};
	
	/**
	 * TODO: ID:
	 * 0 - Air;
	 * 1 - Wall;
	 * 2-12 - Lamps;
	 * 13 - null;
	 * 14 - Generator
	 * 15 - Eater
	 * 16-18 - Valves
	 * 19 - wire
	 * 20 - converter
	 * 21 - amplifier
	 * 22 - Mechanical valve
	 */

	String[] modelFull = new String[] {"Air", "Wall", "Lamp"
			, "Lamp [red]", "Lamp [orange]", "Lamp [yellow]", "Lamp [lime]",
			"Lamp [green]", "Lamp [cyan]", "Lamp [blue]", "Lamp [light-blue]",
			"Lamp [puple]","Lamp [pink]", "Lamp [random]", "Generator", "Eater", "Valve up",
			"Valve right", "Valve left", "wire", "Converter",
			"Light amplifier","Mechanical valve [closed]","Mechanical valve [open]"
			};//"<no added>"
	String[] model = new String[] {"Air", "Wall", "Lamp"
			, "Lamp [red]", "Lamp [orange]", "Lamp [yellow]", "Lamp [lime]",
			"Lamp [green]", "Lamp [cyan]", "Lamp [blue]", "Lamp [light-blue]",
			"Lamp [puple]","Lamp [pink]", "Lamp [random]", "Generator", "Eater", "Valve up",
			"Valve right", "Valve left"};
	
	private boolean[][] isMoved = new boolean[h][w];
	
	int blink = 0;
	private JToggleButton vm;
	private JToggleButton lw;
	
	int mx = 0;
	int my = 0;
	
	int button = -1;
	private int timing = 100;
	private int typeS = 2;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JSvet frame = new JSvet();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public JSvet() {
		

		System.out.println(input.getCode());
		
		isFullVersion = input.IsFV();
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
//				if(1 == (int) (Math.random()*25)) {
//					if(1 == (int) (Math.random()*2)) {
//						type[x][y] = 2;
//					}else {
//						type[x][y] = 1;//2
//					}
//				}else {
					type[x][y] = 0;
//				}
					opend[x][y] = -1;
					lightR[x][y] = 0;//(int) (Math.random()*255);
					lightG[x][y] = 0;//(int) (Math.random()*255);
					lightB[x][y] = 0;//(int) (Math.random()*255);
			}
		}
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setBounds(100, 100, 750, 550);
		setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"\\src\\textures\\ico.png"));
//System.out.println(System.getProperty("user.dir"));
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(new LineBorder(new Color(100, 100, 100)));
		menuBar.setBackground(Color.BLACK);
		menuBar.setOpaque(true);
		menuBar.setForeground(Color.LIGHT_GRAY);
		setJMenuBar(menuBar);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.BLACK);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		contentPane.add(panel, BorderLayout.CENTER);

		JLabel iv = new JLabel(new ImageIcon(img));
		iv.setBorder(new LineBorder(new Color(50,50,50)));
		panel.add(iv);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JButton clear = new JButton("Clear");
		clear.setForeground(Color.WHITE);
		panel_1.add(clear);
		
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int x = 0; x < w; x++) {
					for (int y = 0; y < h; y++) {
						type[x][y] = 0;
						opend[x][y] = -1;
					}
				}
			}
		});

		JButton cr = new JButton("Create rooms");
		cr.setForeground(Color.WHITE);
		panel_1.add(cr);
		
		cr.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO: ROOMS
				for (int x = 0; x < w; x++) {
					for (int y = 0; y < h; y++) {
						if(1 == (int) (Math.random()*5)) {
							type[x][y] = 2;
						}else {
							type[x][y] = 0;
						}
						opend[x][y] = -1;
						lightR[x][y] = 0;
						lightG[x][y] = 0;
						lightB[x][y] = 0;
					}
				}
//				try {
//					Thread.sleep(10);
//				} catch (InterruptedException e) {
//				}
				for (int i = 0; i < 5; i++) {
					for (int x = 0; x < w; x++) {
						for (int y = 0; y < h; y++) {
							AllL(x, y, 1.05);
						}
					}
				}
				for (int x = 0; x < w; x++) {
					for (int y = 0; y < h; y++) {
						if(lightR[x][y] + lightG[x][y] + lightB[x][y] > 450) {
							type[x][y] = 0;
						}else {
							type[x][y] = 1;
							
						}
						lightR[x][y] = 0;
						lightG[x][y] = 0;
						lightB[x][y] = 0;
					}
				}
			}
		});
		
		JButton addl = new JButton("Add Lights");
		addl.setForeground(Color.WHITE);
		panel_1.add(addl);
		
		addl.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO: LIGHTS ADDEDING

				Thread gen = new Thread() {
				
					public void run() {
						for (int x1 = 0; x1 < lightR.length; x1++) {
							for (int y1 = 0; y1 < lightR.length; y1++) {
								for (int i = 0; i < 25; i++) {
									for (int x = 0; x < w; x++) {
										for (int y = 0; y < h; y++) {
											AllL(x, y1, 1.05);
										}
									}
								}
								if(type[x1][y1] == 0) {
									if(lightR[x1][y1] + lightG[x1][y1] + lightB[x1][y1] < 15) {
										type[x1][y1] = 2;
									}
								}
							}
						}
					}
				};
				gen.start();
				
			}
		});

		vm = new JToggleButton("View mode");
		vm.setForeground(Color.WHITE);
		panel_1.add(vm);
		
		lw = new JToggleButton("Lamp -> Water");
		lw.setForeground(Color.WHITE);
		panel_1.add(lw);
		
		WindowsButtons buttons = new WindowsButtons();
		buttons.ConvertToWindowsButton(clear, new Color(75,75,75), new Color(50,50,50), new Color(150,150,150));
		buttons.ConvertToWindowsButton(addl, new Color(75,75,75), new Color(50,50,50), new Color(150,150,150));
		buttons.ConvertToWindowsButton(cr, new Color(75,75,75), new Color(50,50,50), new Color(150,150,150));
		buttons.ConvertToWindowsButton(vm, new Color(75,75,75), new Color(50,50,50), new Color(150,150,150));
		buttons.ConvertToWindowsButton(lw, new Color(75,75,75), new Color(50,50,50), new Color(150,150,150));

		// TODO: comboBox
		
		JComboBox<String> comboBox = new JComboBox<String>();
		if(isFullVersion) {
			comboBox.setModel(new DefaultComboBoxModel<String>(modelFull));

		}else {
			comboBox.setModel(new DefaultComboBoxModel<String>(model));
		}
		comboBox.setSelectedIndex(2);
		comboBox.setMaximumRowCount(50);
		comboBox.setBackground(Color.BLACK);
		comboBox.setFocusable(false);
		panel_1.add(comboBox);
		
		comboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				typeS = comboBox.getSelectedIndex();
			}
		});
		
		iv.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				button = -1;
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				button = e.getButton();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() < 2)
				Replase(e.getX(), e.getY());
			}
		});
		
		iv.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				mx = (int) Math.floor(e.getX()/size);
				my = (int) Math.floor(e.getY()/size);
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				mx = (int) Math.floor(e.getX()/size);
				my = (int) Math.floor(e.getY()/size);
				Replase(e.getX(), e.getY());
			}
		});

		JPanel dataPanel = new JPanel();
		dataPanel.setBackground(Color.BLACK);
		dataPanel.setVisible(false);
		contentPane.add(dataPanel, BorderLayout.SOUTH);

		JLabel fps = new JLabel("FPS: ");
		fps.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fps.setForeground(new Color(175,175,175));
		dataPanel.add(fps);

		itemId = new JLabel("#1");
		itemId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		itemId.setForeground(new Color(175,175,175));
		dataPanel.add(itemId);
		
		// TODO: redraw
		
		Thread redraw = new Thread() {
			
			@Override
			public void run() {
				long start;
				start = System.nanoTime();
				int fpsint = 0;
				while (true) {
					img = null;
					img = new BufferedImage(h*size, w*size, BufferedImage.TYPE_INT_RGB);
					Graphics2D g  = (Graphics2D) img.getGraphics();
					g.setColor(new Color(150,100,75));
					g.fillRect(0, 0, h*size, w*size);

//					long start2 = System.currentTimeMillis();
					
					for (int x = 0; x < w; x++) {
						for (int y = h-1; y > -1; y--) {
							isMoved[x][y] = false;
						}
					}
					
					for (int x = 0; x < w; x++) {
						for (int y = h-1; y > -1; y--) {
							
							AllL(x,y,1.05);
							
							int r = lightR[x][y];
							int gr = lightG[x][y];
							int b = lightB[x][y];
							
							Max(r);
							Max(gr);
							Max(b);
							
							if(type[x][y] == 0) {
								if(vm.isSelected()) {
									g.setColor(new Color(0,0,0));
								}else {
									g.setColor(new Color(r, gr, b));
								}
							}
							if(type[x][y] == 1) {
								if(vm.isSelected()) {
									g.setColor(new Color(150, 100, 75));
								}else {
									g.setColor(new Color(r, (int)(gr/1.5), b/2));
								}
							}
							
							if(type[x][y] > 1 && type[x][y] <  13) {
								g.setColor(new Color(r, gr, b));
								if(lw.isSelected()) {
									if(type[x][(y+1)%h] == 0 && opend[x][(y+1)%h] < 1) {
										if(y < h-1) {
												type[x][(y+1)%h] = type[x][y];
												isMoved[x][(y+1)%h] = true;
												isMoved[x][y] = true;
												type[x][y] = 0;
										}
									}else {
										try {
											if(lightR[x-1][y]+lightG[x-1][y]+lightB[x-1][y] 
													<
											lightR[x+1][y]+lightG[x+1][y]+lightB[x+1][y]) {
												GoX(x, y, -1);
											}else{
												GoX(x, y, +1);
											}
										} catch (ArrayIndexOutOfBoundsException e2) {
										}
									}
								}
							}
							g.fillRect(x*size, y*size, size, size);

							
							if(type[x][y] == 14) {
								GenerateX(x, y, -1, false);
								GenerateX(x, y, 1, false);
								GenerateY(x, y, -1, false);
								GenerateY(x, y, 1, false);
								g.setColor(new Color(255, 255, 255));
								g.fillRect(x*size, y*size, size, size);
								g.setColor(new Color(r, gr, b));
								g.fillOval(x*size, y*size, size-1, size-1);
							}
							if(type[x][y] == 15) {
								Eater(x, y, 0, 1);
								Eater(x, y, 0, -1);
								Eater(x, y, -1, 0);
								Eater(x, y, 1, 0);
								g.setColor(new Color(r, gr, b));
								g.fillRect(x*size, y*size, size, size);
								g.setColor(new Color(255, 255, 255));
								g.fillOval(x*size, y*size, size-1, size-1);
							}
							if(type[x][y] == 16) {
								MoveTo(x, y+1, x, y-1);
								g.setColor(new Color(r/2, gr/2, b/2));
								if(vm.isSelected())
									g.setColor(new Color(50,50,50));
								g.fillRect(x*size, y*size, size, size);
								g.setColor(new Color(r, gr, b));
								if(vm.isSelected())
									g.setColor(new Color(255,255,255));
								g.fillRect(x*size, y*size, size, size/2);
							}
							if(type[x][y] == 17) {
								MoveTo(x-1, y, x+1, y);
								g.setColor(new Color(r/2, gr/2, b/2));
								if(vm.isSelected())
									g.setColor(new Color(50,50,50));
								g.fillRect(x*size, (int) ((y*0.5)*size), size, size/4);
								g.setColor(new Color(r, gr, b));
								if(vm.isSelected())
									g.setColor(new Color(255,255,255));
								g.fillRect((int) ((x+0.5)*size), y*size, size/2, size);
							}
							if(type[x][y] == 18) {
								MoveTo(x+1, y, x-1, y);
								g.setColor(new Color(r/2, gr/2, b/2));
								if(vm.isSelected())
									g.setColor(new Color(50,50,50));
								g.fillRect(x*size, y*size, size, size);
								g.setColor(new Color(r, gr, b));
								if(vm.isSelected())
									g.setColor(new Color(255,255,255));
								g.fillRect(x*size, y*size, size/2, size);
							}
							if(type[x][y] == 19) {
								// WIRE
								g.setColor(Color.BLACK);
								g.fillRect(x*size,y*size,size,size);
								g.setColor(new Color((int)(r/25)*25, (int)(gr/25)*25, (int)(b/25)*25));
								if(vm.isSelected())
									g.setColor(new Color(50,50,50));
								g.fillRect((int) ((x+0.25)*size)+0, (int) ((y+0.25)*size)-1, size/2+2, size/2+2);
								DrawWires(g, x, y, 1, 0);
								DrawWires(g, x, y, 0, 1);
								DrawWires(g, x, y, -1, 0);
								DrawWires(g, x, y, 0, -1);
								g.setStroke(new BasicStroke(1));
							}
							if(type[x][y] == 20) {
								// TO WIRE
								g.setColor(Color.BLACK);
								g.fillRect(x*size,y*size,size,size);
								g.setColor(new Color(r, gr, b));
								if(vm.isSelected())
									g.setColor(new Color(50,50,50));
								g.drawRect((int) (x*size), y*size+1, size-2, size-2);
							}
							if(type[x][y] == 21) {
								r = Amplifier(x, y-1, x, y+1, lightR);
								gr = Amplifier(x, y-1, x, y+1, lightG);
								b = Amplifier(x, y-1, x, y+1, lightB);
								if(r > 255)r = 255;
								if(r < 0)r = 0;
								if(gr > 255)gr = 255;
								if(gr < 0)gr = 0;
								if(b > 255)b = 255;
								if(b < 0)b = 0;
								g.setColor(Color.BLACK);
								g.fillRect(x*size,y*size,size,size);
								g.setColor(new Color(r, gr, b));
								if(vm.isSelected())
									g.setColor(new Color(50,50,50));
								g.fillPolygon(new int[] {x*size,((x+1)*size)-1, (int) ((x+0.6)*size),
										(int) ((x+0.4)*size)-1},
										new int[] { y*size, y*size,(y+1)*size-1,(y+1)*size-1}, 4);
							}
							if(opend[x][y] > -1) {
								g.setColor(Color.BLACK);
								g.fillRect(x*size,y*size,size,size);
								g.setColor(new Color(r, gr, b));
								if(vm.isSelected())
									g.setColor(new Color(50,50,50));
								if(opend[x][y] == 0)
									g.drawRect(x*size,y*size,size-1,size-1);
								g.drawLine(x*size,y*size,(x+1)*size-1,(y+1)*size-1);
								g.drawLine((x+1)*size-1, y*size,x*size,(y+1)*size-1);
								
								if(isWireLight(x+1, y) || isWireLight(x-1, y) || 
										isWireLight(x, y+1) || isWireLight(x, y-1)) {
									opend[x][y] = 0;
								}else {
									opend[x][y] = 1;
								}
							}
						}

						
						/*
						 * Amplifiers(x, y+1, x, y-1, r, gr, b);
								
								g.setColor(Color.BLACK);
								g.fillRect(x*size,y*size,size,size);
								g.setColor(new Color(r, gr, b));
								if(vm.isSelected())
									g.setColor(new Color(50,50,50));
								g.fillPolygon(new int[] {x*size,((x+1)*size)-1, (int) ((x+0.6)*size),
										(int) ((x+0.4)*size)-1},
										new int[] {(y+1)*size-1,(y+1)*size-1, y*size, y*size}, 4);
								lightR[x][y] = 0;
								lightG[x][y] = 0;
								lightB[x][y] = 0;
						 */
//												lightR[x][y] = 0;
//						lightG[x][y] = 0;
//						lightB[x][y] = 0;
//						Amplifiers(x, y-1, x, y+1, r, gr, b); // FIXME
//						g.setColor(Color.BLACK);
//						g.fillRect(x*size,y*size,size,size);
//						g.setColor(new Color(r, gr, b));
//						if(vm.isSelected())
//							g.setColor(new Color(50,50,50));
//						g.fillPolygon(new int[] {x*size,((x+1)*size)-1, (int) ((x+0.6)*size),(int) ((x+0.4)*size)-1},
//								new int[] {y*size, y*size,(y+1)*size-1,(y+1)*size-1}, 4);
//						lightR[x][y] = 0;
//						lightG[x][y] = 0;
//						lightB[x][y] = 0;
					}
//					if(type[x][y] == 24) {
//						Amplifiers(x+1, y, x-1, y, r, gr, b);
//						g.setColor(Color.BLACK);
//						g.fillRect(x*size,y*size,size,size);
//						g.setColor(new Color(r, gr, b));
//						if(vm.isSelected())
//							g.setColor(new Color(50,50,50));
//						g.fillPolygon(new int[] {(x+1)*size-1,(x+1)*size-1,x*size, x*size},
//								new int[] {y*size,((y+1)*size)-1,
//					(int) ((y+0.6)*size),(int) ((y+0.4)*size)-1}, 4);
////						lightR[x][y] = 0;
////						lightG[x][y] = 0;
////						lightB[x][y] = 0;
//					}
//					for (int y = w-1; y < -1; y--) {
//						for (int x = 0; x < w; x++) {
//							int r = lightR[x][y];
//							int gr = lightG[x][y];
//							int b = lightB[x][y];
//							
//							Max(r);
//							Max(gr);
//							Max(b);
//							if(type[x][y] == 22) {
//
//								
//								Amplifiers(x, y-1, x, y+1, r, gr, b); // FIXME
//								g.setColor(Color.BLACK);
//								g.fillRect(x*size,y*size,size,size);
//								g.setColor(new Color(r, gr, b));
//								if(vm.isSelected())
//									g.setColor(new Color(50,50,50));
//								g.fillPolygon(new int[] {x*size,((x+1)*size)-1, (int) ((x+0.6)*size),
//					(int) ((x+0.4)*size)-1},
//										new int[] {y*size, y*size,(y+1)*size-1,(y+1)*size-1}, 4);
////								lightR[x][y] = 0;
////								lightG[x][y] = 0;
////								lightB[x][y] = 0;
//							}
//							if(type[x][y] == 19) {
//								// WIRE
//								g.setColor(Color.BLACK);
//								g.fillRect(x*size,y*size,size,size);
//								g.setColor(new Color((int)(r/25)*25, (int)(gr/25)*25, (int)(b/25)*25));
//								if(vm.isSelected())
//									g.setColor(new Color(50,50,50));
//								g.fillRect((int) ((x+0.25)*size)+0, (int) ((y+0.25)*size)-1, size/2+2, size/2+2);
//								DrawWires(g, x, y, 1, 0);
//								DrawWires(g, x, y, 0, 1);
//								DrawWires(g, x, y, -1, 0);
//								DrawWires(g, x, y, 0, -1);
//								g.setStroke(new BasicStroke(1));
//							}
//						}
//					}
					// TODO

					g.dispose();
					iv.setIcon(new ImageIcon(img));
					iv.repaint();

					//blink++;
					if((System.nanoTime()-start) > 1000000000) {
						start = System.nanoTime();
						fps.setText("FPS: " + fpsint + "/" + 1000/timing);
						fpsint = 0;
					}
					fpsint++;
					try {
						Thread.sleep(timing);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		redraw.start();
		
		// TODO: MENU FILE
		
		JMenu file = new JMenu("File");
		file.setBackground(Color.BLACK);
		file.setForeground(Color.LIGHT_GRAY);
		file.setBorder(new LineBorder(new Color(50,50,50)));
		file.setOpaque(true);
		menuBar.add(file);
		
		JMenuItem newFile = new JMenuItem("New");
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		newFile.setOpaque(true);
		newFile.setBackground(Color.BLACK);
		newFile.setForeground(Color.LIGHT_GRAY);
		newFile.setBorder(new EmptyBorder(2,2,2,2));
		newFile.setOpaque(true);
		file.add(newFile);
		
		newFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		JMenuItem saveFile = new JMenuItem("Save");
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		saveFile.setBackground(Color.BLACK);
		saveFile.setForeground(Color.LIGHT_GRAY);
		saveFile.setBorder(new EmptyBorder(2,2,2,2));
		saveFile.setOpaque(true);
		file.add(saveFile);
		
		saveFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(saveUrl.equals("")) {
					SaveAs();
				}else {
					Save(saveUrl);
				}
			}
		});
		

		JMenuItem saveAsFile = new JMenuItem("Save as");
		saveAsFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		saveAsFile.setBackground(Color.BLACK);
		saveAsFile.setForeground(Color.LIGHT_GRAY);
		saveAsFile.setBorder(new EmptyBorder(2,2,2,2));
		saveAsFile.setOpaque(true);
		file.add(saveAsFile);
		
		saveAsFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SaveAs();
			}
		});

		JMenuItem openFile = new JMenuItem("Open");
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_MASK));
		openFile.setBackground(Color.BLACK);
		openFile.setForeground(Color.LIGHT_GRAY);
		openFile.setBorder(new EmptyBorder(2,2,2,2));
		openFile.setOpaque(true);
		file.add(openFile);
		
		openFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Open");
				if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					File_ file =  new File_();
					String txt = "";
					try {
						txt = file.ReadFile(fc.getSelectedFile() + "");
						String[] allData = txt.split("\n");
						w = Integer.valueOf(allData[0]);
						h = Integer.valueOf(allData[1]);
						char[] text = allData[2].toCharArray();
						char[] opendd = allData[3].toCharArray();
						for (int i = 0; i < text.length; i++) {
							type[(int)Math.floor(i/w)][i%w] = (int)(text[i])-15;
							opend[(int)Math.floor(i/w)][i%w] = (int)(opendd[i])-30;
							lightR[(int)Math.floor(i/w)][i%w] = 0;
							lightG[(int)Math.floor(i/w)][i%w] = 0;
							lightB[(int)Math.floor(i/w)][i%w] = 0;
						}
						saveUrl = fc.getSelectedFile() + "";
						edit = false;
						setTitlee();
					} catch (IOException | NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "Err\n\nMore info:\n" + e1.getMessage());
					}
				}
			}
		});
		
		AddS(file);

		JMenuItem saveScreen = new JMenuItem("Save Screen");
		saveScreen.setBackground(Color.BLACK);
		saveScreen.setForeground(Color.LIGHT_GRAY);
		saveScreen.setBorder(new EmptyBorder(2,2,2,2));
		saveScreen.setOpaque(true);
		file.add(saveScreen);
		AddS(file);
		
		
		saveScreen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Save as Image");
				if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
					File_ file = new File_();
					try {
						file.SaveImage(fc.getSelectedFile() + ".png", img);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Err: Saving error");
					}
				}
			}
		});

//		JMenuItem fullScreen = new JMenuItem("FullScreen");
//		fullScreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
//				InputEvent.CTRL_MASK));
//		fullScreen.setBackground(Color.BLACK);
//		fullScreen.setForeground(Color.LIGHT_GRAY);
//		fullScreen.setBorder(new EmptyBorder(2,2,2,2));
//		fullScreen.setOpaque(true);
//		file.add(fullScreen);
//		
//		fullScreen.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				menuBar.setVisible(false);
//				clear.setVisible(false);
//				vm.setVisible(false);
//				cr.setVisible(false);
//				addl.setVisible(false);
//				lw.setVisible(false);
//				comboBox.setVisible(false);
//				panel_1.setVisible(false);
//			}
//		});
		
		// TODO: MENU GAME
		
		JMenu gameJMenu = new JMenu("Game");
		gameJMenu.setBackground(Color.BLACK);
		gameJMenu.setForeground(Color.LIGHT_GRAY);
		gameJMenu.setBorder(new LineBorder(new Color(50,50,50)));
		gameJMenu.setOpaque(true);
		menuBar.add(gameJMenu);
		
		JCheckBoxMenuItem gameInfo = new JCheckBoxMenuItem("Show game info");
		gameInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				InputEvent.CTRL_MASK));
		gameInfo.setBackground(Color.BLACK);
		gameInfo.setForeground(Color.LIGHT_GRAY);
		gameInfo.setBorder(new EmptyBorder(2,2,2,2));
		gameInfo.setOpaque(true);
		gameJMenu.add(gameInfo);
		
		gameInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dataPanel.setVisible(gameInfo.isSelected());
			}
		});
		
		JPanel speedJPanel = new JPanel();
		speedJPanel.setBackground(Color.BLACK);
		gameJMenu.add(speedJPanel);
		
		JLabel speedJLabel = new JLabel("Speed");
		speedJLabel.setForeground(Color.LIGHT_GRAY);
		speedJLabel.setBackground(Color.BLACK);
		speedJLabel.setOpaque(true);
		speedJPanel.add(speedJLabel);
		
		JSlider speedSlider = new JSlider();
		speedSlider.setOpaque(true);
		speedSlider.setFocusable(false);
		speedSlider.setMinimum(1);
		speedSlider.setMaximum(500);
		speedSlider.setValue(timing);
		speedSlider.setBackground(Color.BLACK);
		speedJPanel.add(speedSlider);
		
		
		speedSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				timing = 501 - speedSlider.getValue();
			}
		});
		
		if(!isFullVersion) {
			JMenuItem code = new JMenuItem("Activate full version");
			code.setBackground(Color.BLACK);
			code.setForeground(Color.LIGHT_GRAY);
			code.setBorder(new EmptyBorder(2,2,2,2));
			code.setOpaque(true);
			gameJMenu.add(code);
			
			code.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					input.ActivateCode();
					isFullVersion = input.IS_FULL_VERSION;
					if(isFullVersion) {
						code.setVisible(false);
						comboBox.setModel(new DefaultComboBoxModel<String>(modelFull));
					}
				}
			});
		}
	}
	
	private void Save(String url) {
		String text = "" + w + "\n" + h + "\n";
		String text2 = "";
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				text += (char) (type[x][y] + 15);
				text2 += (char) (opend[x][y] + 30);
			}
		}
		text += "\n" + text2;
		File_ file =  new File_();
		try {
			file.WriteFile(url, text);//, "ACSII");
			edit = false;
			setTitlee();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Err: Saving error");
		}
	}
	
	private void SaveAs() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save");
		if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			saveUrl = fc.getSelectedFile() + ".light2";
			Save(saveUrl);
		}
	}
	
	public void setTitlee() {
		if(edit) {
			setTitle(title + " " + saveUrl + "*");
		}else {
			setTitle(title + " " + saveUrl);
		}
	}
	
	private int Amplifier(int x, int y, int nx, int ny, int light[][]) {
		try {
			if(isWires[type[x][y]]) {
				light[nx][ny] = light[x][y]*5;
				return light[x][y]*5;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return 0;
	}
	
	private void DrawWires(Graphics2D g, int x, int y, int px, int py) {
		try {
			int typeXY = type[x+px][y+py];
			if(typeXY == 19 || typeXY == 20) {
				g.setStroke(new BasicStroke(size/2));
				g.drawLine((int) ((x+0.5)*size), (int) ((y+0.5)*size)-1,
						(int) ((x+0.5+(px*0.5))*size),  (int) ((y+0.5+(py*0.4))*size)-1);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	private void GenerateX(int x, int y, int px, boolean move) {
		try {
			if(type[x+px][y] > 1 && type[x+px][y] < 13) {
				if(type[x-px][y] == 0) {
					type[x-px][y] = type[x+px][y];
					if(move)
						type[x+px][y] = 0;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}
	
	private void MoveTo(int x, int y, int nx, int ny) {
		try {
			if(isMoved[nx][ny]) {
				return;
			}
			if(type[nx][ny] == 0) {
				if(type[x][y] > 1 && type[x][y] < 13) {
					type[nx][ny] = type[x][y];
					type[x][y] = 0;
					isMoved[nx][ny] = true;
					isMoved[x][y] = true;
				}
			}
		}catch (ArrayIndexOutOfBoundsException e) {
		}
	}
	
	private void GenerateY(int x, int y, int py, boolean move) {
		try {
			if(type[x][y+py] > 1 && type[x][y+py] < 13) {
				if(type[x][y-py] == 0) {
					type[x][y-py] = type[x][y+py];
					if(move)
						type[x][y+py] = 0;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}
	
	public boolean isWireLight(int x, int y) {
		try {
			return type[x][y] == 19 && (lightR[x][y] > 25 || lightG[x][y] > 25 || lightB[x][y] > 25);
		} catch (ArrayIndexOutOfBoundsException e) {
			return true;
		}
	}
	
	private void Eater(int x, int y, int px, int py) {
		try {
			if(type[x+px][y+py] > 1 && type[x+px][y+py] < 13
					) {
				type[x+px][y+py] = 0;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}
	
	private void AllL(int x, int y, double v) {
		Logic(x, y, lightR, v);
		Logic(x, y, lightG, v);
		Logic(x, y, lightB, v);
	}
	
	private void GoX(int x, int y, int px) {
		if(isMoved[x][y])
			return;
		if(type[x+px][y] == 0 && opend[x+px][y] < 1) {
				type[x+px][y] = type[x][y];
				type[x][y] = 0;
				isMoved[x+px][y] = true;
		}else {
			if(type[x-px][y] == 0 && opend[x-px][y] < 1) {
				type[x-px][y] = type[x][y];
				type[x][y] = 0;
				isMoved[x-px][y] = true;
			}
		}
	}
	
	private void Replase(int x, int y) {
		if(!(button == 3)) {
			try {
				int nx = (int) Math.floor(x/size);
				int ny = (int) Math.floor(y/size);
				int r =typeS;
				if(r == 13) {
					r = (int) (2 + (Math.random()*11));
				}
				if(r == 23 || r == 22) {
					if(r == 23)
						opend[nx][ny] = 0;
					if(r == 22)
						opend[nx][ny] = 1;
					r = 0;
				}else {
					opend[nx][ny] = -1;
				}
				type[nx][ny] = r;
				itemId.setText("#" + r + " | #" + typeS);
				
				if(!edit) {
					edit = true;
					setTitlee();
				}
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		}
	}
	
	private void Logic(int x, int y, int[][] light, double value) {

		// TODO: L
		
		double dm = (int) Math.sqrt((mx-x)*(mx-x) + (my-y)*(my-y));

		Lamps(x, y);
		
		if(dm < 5 && !(type[x][y] == 2) && button == 3) {
			light[x][y] = (int) (2500-dm*500);
		}
		
		int delit = 1;
    	int plus = light[x][y];
    	
    	int typexy = type[x][y];
    	
    	boolean lt = typexy < 2 || typexy > 13;//!HaveId(wallsId, typexy); //
    	
    	if(typexy == 19) {// || typexy == 20
				delit = 1;
				plus = 0;
    			if(x + 1 < w) {
    				if(isWires[type[x+1][y]] || type[x+1][y]==20) {
    					plus = plus + light[x+1][y];
    					if(type[x+1][y]==20)
        					plus = plus + light[x+1][y]*3;
    					delit++;
    				}
    			}
    			if(x - 1 > 0) {
    				if(isWires[type[x-1][y]] || type[x-1][y]==20) {
    					plus = plus + light[x-1][y];
    					if(type[x-1][y]==20)
        					plus = plus + light[x-1][y]*3;
    					delit++;
    				}
    			}
    			if(y + 1 < h) {
    				if(isWires[type[x][y+1]] || type[x][y+1]==20) {
    					plus = plus + light[x][y+1];
    					if(type[x][y+1]==20)
        					plus = plus + light[x][y+1]*3;
    					delit++;
    				}
    			}
				if(y - 1 > 0) {
    				if(isWires[type[x][y-1]] || type[x][y-1]==20) {
    					plus = plus + light[x][y-1];
    					if(type[x][y-1]==20)
        					plus = plus + light[x][y-1]*3;
    					delit++;
    				}
    			}
				if(delit > 1) {
		    		light[x][y] = (int) (plus / (delit-1));
					light[x][y] = (int) (light[x][y]-0.5);//1.05
				}else {
//					light[x][y] = 0;
				}
    	}else {
    		if(lt) {
    			if(x + 1 < w) {
    				if(!isWalls[type[x+1][y]]) {//!(type[x+1][y]==1) && !
    					plus = plus + light[x+1][y];
    					delit++;
    				}
    			}
    		}
    		if(lt) {
    			if(x - 1 > 0) {
    				if(!isWalls[type[x-1][y]]) {//!(type[x-1][y]==1) && !
    					plus = plus + light[x-1][y];
    					delit++;
    				}
    			}
    		}
    		if(lt) {
    			if(y + 1 < h) {
    				if(!isWalls[type[x][y+1]]) {//!(type[x][y+1]==1) && 
    					plus = plus + light[x][y+1];
    					delit++;
    				}
    			}
    		}
    		if(lt) {
    			if(y - 1 > 0) {
    				if(!isWalls[type[x][y-1]]) {
    					plus = plus + light[x][y-1];
    					delit++;
    				}
    			}
    		}
    		light[x][y] = plus / delit;
    		light[x][y] = (int) (light[x][y]/1.05);//1.05
    		if(typexy == 20) {
        		light[x][y] = (int) (light[x][y]/2);//1.05
    		}
		}
    	
    	if(light[x][y] > 255) {
    		light[x][y] = 255;
    	}
    	if(light[x][y] < 0) {
    		light[x][y] = 0;
    	}
	}
	
	private void Lamps(int x, int y) {
		if(type[x][y] == 2) {
			lightR[x][y] = 255;
			lightG[x][y] = 255;
			lightB[x][y] = 255;
		}
		if(type[x][y] == 3) {
			lightR[x][y] = 255;
			lightG[x][y] = 0;
			lightB[x][y] = 0;
		}
		if(type[x][y] == 4) {
			lightR[x][y] = 255;
			lightG[x][y] = 180;
			lightB[x][y] = 0;
		}
		if(type[x][y] == 5) {
			lightR[x][y] = 255;
			lightG[x][y] = 255;
			lightB[x][y] = 0;
		}
		if(type[x][y] == 6) {
			lightR[x][y] = 200;
			lightG[x][y] = 255;
			lightB[x][y] = 0;
		}
		if(type[x][y] == 7) {
			lightR[x][y] = 0;
			lightG[x][y] = 255;
			lightB[x][y] = 0;
		}
		if(type[x][y] == 8) {
			lightR[x][y] = 0;
			lightG[x][y] = 255;
			lightB[x][y] = 180;
		}
		if(type[x][y] == 9) {
			lightR[x][y] = 0;
			lightG[x][y] = 0;
			lightB[x][y] = 255;
		}
		if(type[x][y] == 10) {
			lightR[x][y] = 0;
			lightG[x][y] = 180;
			lightB[x][y] = 255;
		}
		if(type[x][y] == 11) {
			lightR[x][y] = 180;
			lightG[x][y] = 0;
			lightB[x][y] = 255;
		}
		if(type[x][y] == 12) {
			lightR[x][y] = 255;
			lightG[x][y] = 0;
			lightB[x][y] = 255;
		}
	}
	
	private void Max(int i) {
		if(i > 255) {
    		i = 255;
    	}
    	if(i < 0) {
    		i = 0;
    	}
	}

	private void AddS(JMenu menu) {
		JSeparator separator = new JSeparator();
		menu.add(separator);
	}
}

/*
 * 
 * 
 * JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Save");
//				fc.setFileSelectionMode(1);
				if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
					String text = "" + w + "\n" + h + "\n";
					String text2 = "";
					for (int x = 0; x < w; x++) {
						for (int y = 0; y < h; y++) {
							text += (char) (type[x][y] + 15);
							text2 += (char) (opend[x][y] + 30);
						}
//						text += "\n";
					}
					text += "\n" + text2;
					File_ file =  new File_();
					try {
						file.WriteFile(fc.getSelectedFile() + ".light2", text);//, "ACSII");
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Err: Saving error");
					}
				}
*/