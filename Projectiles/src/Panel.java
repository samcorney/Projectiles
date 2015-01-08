import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

public class Panel extends JPanel implements MouseListener, MouseMotionListener, KeyListener, Runnable {

    private List<Projectile> projectiles = new ArrayList<Projectile>();
    private double initialAngle = 40;
    private int angleFinderLength = 30;
    private Point launchPoint = new Point(20, 700);
    private double gravity = -119.81;
    private double initialVelocity = 50;
    private DecimalFormat df = new DecimalFormat("#.##");
    private double timeInterval = 0.025;

    public Panel(){
        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void paint(Graphics g){
        super.paintComponent(g);

        g.drawLine(launchPoint.x, launchPoint.y, launchPoint.x
                + (int)(Math.cos(degToRad(initialAngle)) * angleFinderLength), launchPoint.y
                - (int)(Math.sin(degToRad(initialAngle)) * angleFinderLength));

        g.drawLine(0, launchPoint.y, 2000, launchPoint.y);

        for(Projectile projectile : getProjectiles()){
            g.drawRect(projectile.getPoint().x, (projectile.getPoint().y), 1, 1);
        }

        g.drawString("Angle: " + df.format(initialAngle), launchPoint.x, launchPoint.y + 20);
        g.drawString("Initial Velocity: " + df.format(initialVelocity), launchPoint.x, launchPoint.y + 40);

    }

    private double degToRad(double degrees){
        return degrees * (Math.PI / 180);
    }

    private double radToDeg(double radians){
        return radians * (180 / Math.PI);
    }

    private void doStuff(){
        for(Projectile projectile : getProjectiles()){
            projectile.updateDisplacement(gravity);
            projectile.setTimeFromLaunch(projectile.getTimeFromLaunch() + timeInterval);
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        getProjectiles().add(new Projectile(new Point(launchPoint.x, launchPoint.y), initialVelocity, initialAngle));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if(e.getX() >= launchPoint.x && e.getY() <= launchPoint.getY()){
            double adjacent = Math.abs(launchPoint.x - e.getX());
            double opposite = Math.abs(launchPoint.y - e.getY());
            initialAngle = radToDeg(Math.atan2(opposite, adjacent));
            initialVelocity = Math.sqrt((adjacent * adjacent) + (opposite * opposite)) / 2;
        }
    }

    @Override
    public synchronized void run() {

        while (true){
            try {
                wait((long)(1000 * timeInterval));
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            doStuff();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public synchronized List<Projectile> getProjectiles(){
        return projectiles;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT :
                if(initialAngle < 90){
                    initialAngle++;
                }
                break;
            case KeyEvent.VK_RIGHT :
                if(initialAngle > 0){
                    initialAngle--;
                }
                break;
            case KeyEvent.VK_UP :
                initialVelocity++;
                break;
            case KeyEvent.VK_DOWN :
                if(initialVelocity > 0){
                    initialVelocity--;
                }
                break;
            case KeyEvent.VK_SPACE :
                getProjectiles().add(new Projectile(new Point(launchPoint.x, launchPoint.y), initialVelocity, initialAngle));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
