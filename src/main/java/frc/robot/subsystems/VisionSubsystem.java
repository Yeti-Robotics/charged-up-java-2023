package frc.robot.subsystems;


import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import javax.inject.Inject;
import javax.inject.Named;


public class VisionSubsystem extends SubsystemBase {
    PhotonPipelineResult result;
    public double xFinal;
    public double yFinal;

    public static PhotonTrackedTarget aprilTag;
    PhotonCamera camera;
    @Inject

    public VisionSubsystem(@Named("camera") PhotonCamera camera) {
        this.camera = camera;
    }

    public double getYaw(){
        return aprilTag.getYaw();
    }
    public double getPitch(){
        return aprilTag.getPitch();
    }

    public double getDistance(PhotonTrackedTarget temp){
        return Units.metersToFeet(
                PhotonUtils.calculateDistanceToTargetMeters(Units.inchesToMeters(29.5), Units.inchesToMeters(104.5),
                        Units.degreesToRadians(45),
                        Units.degreesToRadians(temp.getPitch())));
    }

    public int getID(){
        return aprilTag.getFiducialId();
    }
    @Override
    public void periodic() {
        result = camera.getLatestResult();
        if(result.hasTargets()){
            aprilTag = result.getBestTarget();
        }
        double angle = aprilTag.getPitch();
        double realDistance = getDistance(aprilTag);
        double horzDistance = realDistance*Math.cos(angle);
        double thetaTemp = 0; //add drivetrainsubsystem.getyaw
        xFinal = horzDistance*Math.sin(thetaTemp);
        yFinal = horzDistance *Math.cos(thetaTemp) - Constants.VisionConstants.STOPPING_DISTANCE;
    }
}

