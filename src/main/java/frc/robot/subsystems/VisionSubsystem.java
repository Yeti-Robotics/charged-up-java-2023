package frc.robot.subsystems;


import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import javax.inject.Inject;
import javax.inject.Named;


public class VisionSubsystem extends SubsystemBase {
    private final NetworkTableInstance table;
    PhotonPipelineResult result;
    public double xFinal;
    public double yFinal;

    public VisionSubsystem(@Named(Constants.VisionConstants.TABLE_NAME) NetworkTableInstance table) {
        this.table = table;
    }

    private NetworkTableEntry getValue(String key){
        return table.getTable("limelight").getEntry(key);
    }

    public boolean hasTargets() {
        return getValue("tv").getDouble(0) == 1;
    }

    public double getX() {
        return getValue("tx").getDouble(0.00);
    }

    public double getY() {
        return getValue("ty").getDouble(0.00);
    }

    public double[] getPose() {
        return getValue("botpose").getDoubleArray(new double[6]);
    }

    public double getYaw(){
        return getPose()[5];
    }
    public double getPitch(){
        return getPose()[4];
    }

    public int getID(){
        return (int) getValue("tid").getInteger(0);
    }
    public void setPipeline(int num) {
        getValue("pipeline").setNumber(num);
    }
}

