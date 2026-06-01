package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Drivetrain {
    private DcMotor Fr, Fl, Br, Bl;
    private IMU imu;

    public void init(HardwareMap hwMap){
        Fr = hwMap.get(DcMotor.class, "frontRight");
        Fl = hwMap.get(DcMotor.class, "frontLeft");
        Br = hwMap.get(DcMotor.class, "backRight");
        Bl = hwMap.get(DcMotor.class, "backLeft");

        Fr.setDirection(DcMotorSimple.Direction.REVERSE);
        Br.setDirection(DcMotorSimple.Direction.REVERSE);

        Fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hwMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        imu.initialize(new IMU.Parameters(RevOrientation));

    }

    public void Drive(double forward, double strafe, double rotate){
        double FrP = forward + strafe + rotate;
        double FlP = forward - strafe + rotate;
        double BrP = forward - strafe - rotate;
        double BlP = forward - strafe + rotate;

        double maxPower = 1.0;
        double maxSpeed = 1.0;

        maxPower = Math.max(maxPower, Math.abs(FrP));
        maxPower = Math.max(maxPower, Math.abs(FlP));
        maxPower = Math.max(maxPower, Math.abs(BrP));
        maxPower = Math.max(maxPower, Math.abs(BlP));

        Fr.setPower(maxSpeed * (FrP / maxPower));
        Fl.setPower(maxSpeed * (FlP / maxPower));
        Br.setPower(maxSpeed * (BrP / maxPower));
        Bl.setPower(maxSpeed * (BrP / maxPower));
    }

    public void driveFieldRelative(double forward, double strafe, double rotate){
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);

        theta = AngleUnit.normalizeRadians(theta -
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        this.Drive(newForward, newStrafe, rotate);
    }
}
