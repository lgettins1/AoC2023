import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class AoCDay20b {
        static class Module {
            String type; String[] targets; String[] states;
            ArrayList<String> inputList;
            public Module(String _type, String[] _targets, ArrayList<String> _inputList,String[] _states){
                type = _type; targets = _targets; inputList = _inputList; states = _states;
            }
        }
        public static void main(String[] args) {

            HashMap<String, Module> moduleMap = new LinkedHashMap<>();
            String thisLine;
            try {
                BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day20input.txt"));
                while ((thisLine = br.readLine()) != null) {
                    String[] a = thisLine.split(">");
                    String[] b = a[0].split(" ");
                    String[] targets = a[1].split(",");
                    String mod;
                    String type = "bCast";
                    if (b[0].equals("broadcaster")) {
                        mod = b[0];
                    } else {
                        mod = b[0].substring(1);
                        if (b[0].charAt(0) == '%') {
                            type = "flip";
                        } else {
                            type = "con";
                        }
                    }
                    ArrayList<String> il = new ArrayList<>();
                    String[] is = new String[20];
                    moduleMap.put(mod, new Module(type, targets, il, is));
                }
                ArrayList<String> keys = new ArrayList<>(moduleMap.keySet());
                for (String key : keys) {
                    int tc = moduleMap.get(key).targets.length;
                    String[] ts = moduleMap.get(key).targets;
                    for (int b = 0; b < tc; b++) {
                        Module tgt = moduleMap.get(ts[b].trim());
                        if(!(tgt == null)){
                            if(tgt.type.equals("con")) {
                                tgt.inputList.add(key);
                                tgt.states[tgt.inputList.size() - 1] = "low";
                            }
                        }
                    }
                    if(moduleMap.get(key).type.equals("flip")) moduleMap.get(key).states[0] = "off";
                }
                ArrayList<String[]> steps = new ArrayList<>();
                long buttonPushes = 1;
                ArrayList<Integer> tn = new ArrayList<>();
                ArrayList<Integer> st = new ArrayList<>();
                ArrayList<Integer> hh = new ArrayList<>();
                ArrayList<Integer> dt = new ArrayList<>();

                while(buttonPushes < 10000) {
                    steps.add(new String[]{"button","broadcaster", "low"});

                    while(steps.size() > 0){
                        String[] readStep = steps.get(0);
                        String source = readStep[0].trim();
                        String mod = readStep[1].trim();
                        String pulse = readStep[2].trim();
                        Module modInfo = moduleMap.get(mod);
                        if(modInfo == null) {
                            steps.remove(0);
                            continue;
                        }
                        String[] tg = modInfo.targets;
                        String fState = modInfo.states[0];

                        switch(modInfo.type){
                            case "bCast":
                                for (String s : tg) {
                                   String[] newStep = {mod,s,pulse};
                                    steps.add(newStep);
                                }
                                break;
                            case "flip":
                                if(!(pulse.equals("high"))){
                                    String p;
                                    if(fState.equals("off")){
                                        p = "high";
                                        modInfo.states[0] = "on";
                                    } else {
                                        p = "low";
                                        modInfo.states[0] = "off";
                                    }
                                    for (String s : tg) {
                                        String[] newStep = {mod,s,p};
                                        steps.add(newStep);
                                    }
                                }
                                break;
                            case "con":
                                for(int ipCount = 0; ipCount < modInfo.inputList.size(); ipCount ++){
                                    if(modInfo.inputList.get(ipCount).equals(source)){
                                        modInfo.states[ipCount] = pulse;
                                        break;
                                    }
                                }
                                String op = "low";
                                for(int ipCount = 0; ipCount < modInfo.inputList.size(); ipCount ++){
                                    if (modInfo.states[ipCount].equals("low")) {
                                        op = "high";
                                        break;
                                    }
                                }
                                for (String s : tg) {
                                    String[] newStep = {mod,s,op};
                                    if(s.trim().equals("lv") && op.equals("high")){
                                        if(mod.equals("st")) st.add((int) buttonPushes);
                                        if(mod.equals("tn")) tn.add((int) buttonPushes);
                                        if(mod.equals("hh")) hh.add((int) buttonPushes);
                                        if(mod.equals("dt")) dt.add((int) buttonPushes);
                                    }
                                    steps.add(newStep);
                                }
                        }
                        steps.remove(0);
                    }
                    buttonPushes ++;
                }

                BigInteger answer = lcm(BigInteger.valueOf(st.get(0)), lcm( BigInteger.valueOf(tn.get(0))
                        ,lcm(BigInteger.valueOf(hh.get(0)), BigInteger.valueOf(dt.get(0)))));
                System.out.println("The answer is " + answer);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    public static BigInteger lcm(BigInteger number1, BigInteger number2) {
        BigInteger gcd = number1.gcd(number2);
        BigInteger absProduct = number1.multiply(number2).abs();
        return absProduct.divide(gcd);
    }
}


