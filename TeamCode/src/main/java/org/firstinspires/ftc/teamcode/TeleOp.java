package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {

    DcMotor TestCatapult;

    public void Delay(int mills){
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() {
        TestCatapult = hardwareMap.get(DcMotor.class, "catapult");
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            TestCatapult.setPower(-1.0);
            Delay(75);
            TestCatapult.setPower(1.0);
            Delay(75);
        }else{
            TestCatapult.setPower(0.0);
        }
    }
}
