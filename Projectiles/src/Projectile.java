import java.awt.*;

public class Projectile{

    private Point point;
    private Point launchPoint;
    private double timeFromLaunch;
    private double initialVelocity;
    private double angleOfLaunch;
    private double coefficientOfRestitution = 0.3;

    public Projectile(Point launchPoint, double initialVelocity, double angleOfLaunch) {
        this.initialVelocity = initialVelocity;
        this.angleOfLaunch = angleOfLaunch;
        this.timeFromLaunch = 0;
        this.point = new Point(launchPoint.x, launchPoint.y);
        this.launchPoint = launchPoint;
    }

    public Point getPoint() {
        return point;
    }

    public double getTimeFromLaunch() {
        return timeFromLaunch;
    }

    public void setTimeFromLaunch(double timeFromLaunch) {
        this.timeFromLaunch = timeFromLaunch;
    }

    public void updateDisplacement(double acceleration){
        updateYDisplacement(acceleration);
        updateXDisplacement();

    }

    private double getInitialHorizontalVelocity(){
        return Math.cos((angleOfLaunch) * (Math.PI / 180)) * initialVelocity;
    }

    private double getInitialVerticalVelocity(){
        return Math.sin((angleOfLaunch) * (Math.PI / 180)) * initialVelocity;
    }

    private void updateXDisplacement(){
        point.x = (int)((getInitialHorizontalVelocity() * timeFromLaunch) + launchPoint.x);
    }

    private void updateYDisplacement(double acceleration){
        int newY = (int)((
                (getInitialVerticalVelocity() * timeFromLaunch)
                    +
                (0.5 * acceleration * (timeFromLaunch * timeFromLaunch))
        )
        //flip the horizontal axis
        * -1
        +  launchPoint.y);

        if(newY > launchPoint.y){
            initialVelocity = initialVelocity * coefficientOfRestitution;
            launchPoint.x = point.x;
            launchPoint.y = point.y;
            timeFromLaunch = 0;
            updateDisplacement(acceleration);
        }
        else {
            point.y = newY;
        }
    }
}