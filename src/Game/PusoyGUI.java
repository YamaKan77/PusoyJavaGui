package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PusoyGUI
{
	public static void main(String[] args) 
	{
        JFrame window = new JFrame("Pusoy");     
        JTextField tf = new JTextField();
        tf.setBounds(50, 50, 150, 20);
        JButton newGame = new JButton( "New Game" );
        newGame.setBounds(50, 100, 95, 30);
        newGame.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		tf.setText("Clicked");
        	}
        });
        
        window.add(newGame);
        window.add(tf);
        window.setSize(400,400);
        window.setLayout(null);
        window.setVisible(true);
    }
	
	

}
