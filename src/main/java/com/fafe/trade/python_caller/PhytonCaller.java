package com.fafe.trade.python_caller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class PhytonCaller {

	private final String pythonPrjPath;
	private final String scriptName;
	private final String[] args;

	public PhytonCaller(String pythonPrjPath, String scriptName, String args[]) {
		this.pythonPrjPath = pythonPrjPath;
		this.scriptName = scriptName;
		this.args = args;
	}

	// python analysis.py 2017 1 20
	// python order_generator.py
	public void call() throws Exception {
		try {

			// ProcessBuilder pb = new ProcessBuilder("python",
			// "C:/Users/fafe/workspace/BollingerBand/analysis.py", "2017", "1",
			// "14", "2017", "1", "20");
			// ProcessBuilder pb = new ProcessBuilder("python",
			// "C:/Users/fafe/workspace/BollingerBand/order_generator.py");
			List<String> commands = new LinkedList<String>();
			commands.add("python");
			commands.add(pythonPrjPath + File.separator + scriptName);
			if (args != null && args.length > 0) {
				for (String arg : args) {
					commands.add(arg);
				}
			}
			ProcessBuilder pb = new ProcessBuilder(commands);
			Process p = pb.start();

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
			// System.out.println("Here is the standard output of the
			// command:\n");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			boolean error = false;
			while ((s = stdError.readLine()) != null) {
				if (!error) {
					System.out.println("ERROR:\n");
					error = true;
				}
				System.out.println(s);
			}

			if (error) {
				throw new Exception("Error while calling " + scriptName);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public static void main(String[] args) {
		String[] params = { "2017", "5", "12", "MI" };
		PhytonCaller pc = new PhytonCaller("C:/Users/fafe/workspace/BollingerBand/", "analysis.py", params);
		try {
			pc.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] orderParams = { "MI", "1", "-0.95", "4" };

		pc = new PhytonCaller("C:/Users/fafe/workspace/BollingerBand/", "order_generator.py", orderParams);
		try {
			pc.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
