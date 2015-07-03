package com.example.aatdevelopment;


import android.os.Bundle;
import ioio.lib.api.AnalogInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import android.view.WindowManager;
import android.widget.ToggleButton;

public class HardwareTest extends IOIOActivity {

	// Target LED Button declarations
	ToggleButton Target1LED;
	ToggleButton Target2LED;
	ToggleButton Target3LED;
	ToggleButton Target4LED;
			
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hardware_test);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Keep screen awake

		// assign io's to buttons
		Target1LED = (ToggleButton) findViewById(R.id.tbTarget1LED);
		Target2LED = (ToggleButton) findViewById(R.id.tbTarget2LED);
		Target3LED = (ToggleButton) findViewById(R.id.tbTarget3LED);
		Target4LED = (ToggleButton) findViewById(R.id.tbTarget4LED);
	}
	
	class IOIOThread extends BaseIOIOLooper { 

		private DigitalOutput out1;
		private DigitalOutput out3;
		private DigitalOutput out5;
		private DigitalOutput out7;
		private AnalogInput in40;
		private AnalogInput in41;
		private AnalogInput in42;
		private AnalogInput in43;

		@Override
		protected void setup() throws ConnectionLostException {
			// assign ioio pins
			out1 = ioio_.openDigitalOutput(1, true);
			out3 = ioio_.openDigitalOutput(3, true);
			out5 = ioio_.openDigitalOutput(5, true);
			out7 = ioio_.openDigitalOutput(7, true);
			in40 = ioio_.openAnalogInput(40);
			in41 = ioio_.openAnalogInput(41);
			in42 = ioio_.openAnalogInput(42);
			in43 = ioio_.openAnalogInput(43);
		}

		 public void loop() throws ConnectionLostException, InterruptedException {
			 try {
				out1.write(Target1LED.isChecked());
				out3.write(Target2LED.isChecked());
				out5.write(Target3LED.isChecked());
				out7.write(Target4LED.isChecked());
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}				
										
			try {									
				if (in40.read()>.05&&Target1LED.isChecked()) {  // Turn off target 1 LED if it's on, target 1 switch is triggered 
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Target1LED.setChecked(false);														
						}
					});
				}
				// Turn off target 2 LED if target 2 switch is triggered
				if (in41.read()>.05&&Target2LED.isChecked()) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Target2LED.setChecked(false);																					
						}
					});
				}
				// Turn off target 3 LED if target 3 switch is triggered
				if (in42.read()>.05&&Target3LED.isChecked()) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Target3LED.setChecked(false);																					
						}
					});
				}
				// Turn off target 4 LED if target 4 switch is triggered
				if (in43.read()>.05&&Target4LED.isChecked()) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Target4LED.setChecked(false);														
						}
					});
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	}

	@Override
	protected IOIOLooper createIOIOLooper() {
		return new IOIOThread();
	}
}
