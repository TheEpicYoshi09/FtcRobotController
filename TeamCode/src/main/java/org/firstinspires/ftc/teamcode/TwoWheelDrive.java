package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;


public class TwoWheelDrive {
    private DcMotor Br, Bl;

    public double Power;
    public void init(HardwareMap hwMap){
        Br = hwMap.get(DcMotor.class, "backRight");
        Bl = hwMap.get(DcMotor.class, "backLeft");

        Br.setDirection(DcMotorSimple.Direction.REVERSE);

        Br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void addPower(){
        Power += 0.1;
    }
    public void subtractPower(){
        Power -= 0.1;
    }



}
