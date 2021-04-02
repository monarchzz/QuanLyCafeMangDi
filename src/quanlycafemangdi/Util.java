/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author monar
 */
public class Util {
    public static void doiPanel(JLayeredPane jLayeredPane,JPanel panel){
        jLayeredPane.removeAll();
        jLayeredPane.add(panel);
        jLayeredPane.repaint();
        jLayeredPane.revalidate();
    }
}
