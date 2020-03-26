/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pcodemachine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Daniel
 */
public class PcodeInterpreter {

    private LinkedList<String> stack;
    private String[] memory;
    private String input;
    private List<String> instructions;
    private int currentInstruction;
    private boolean halt;
    private boolean waitForInput;

    public PcodeInterpreter() {
    }

    public void reset(String program) {
        StringTokenizer stProgram = new StringTokenizer(program, "\n\r");
        instructions = new ArrayList<String>();
        while (stProgram.hasMoreTokens()) {
            instructions.add(stProgram.nextToken());
        }
        stack = new LinkedList<String>();
        memory = new String[20];
        currentInstruction = 0;
        halt = false;
        waitForInput = false;
    }

    public int getCurrentInstruction() {
        return currentInstruction;
    }

    public void setInput(String input) {
        if (input == null || input.trim().length() == 0) {
            this.input = null;
        } else {
            this.input = input;
        }
    }

    public boolean isHalted() {
        return halt;
    }

    public boolean isWaitingForInput() {
        return waitForInput;
    }

    public String step() {
        waitForInput = false;
        if (!halt) {
            String instruction = instructions.get(currentInstruction++);
            String instructionValue = getInstructionValue(instruction);
            if (instruction.startsWith("rdi")) {
                if (input == null) {
                    currentInstruction--;
                    waitForInput = true;
                    return "Digite um valor na entrada!";
                }
                int address = Integer.parseInt(stack.pop());
                memory[address] = input;
                input = null;
            } else if (instruction.startsWith("wri")) {
                return stack.pop();
            } else if (instruction.startsWith("lda")) {
                stack.push(instructionValue);
            } else if (instruction.startsWith("ldc")) {
                stack.push(getInstructionValue(instruction));
            } else if (instruction.startsWith("lod")) {
                stack.push(memory[Integer.parseInt(instructionValue)]);
            } else if (instruction.startsWith("mpi")) {
                int op1 = Integer.parseInt(stack.pop());
                int op2 = Integer.parseInt(stack.pop());
                int result = op1 * op2;
                stack.push(Integer.toString(result));
            } else if (instruction.startsWith("dvi")) {
                int op1 = Integer.parseInt(stack.pop());
                int op2 = Integer.parseInt(stack.pop());
                int result = op2 / op1;
                stack.push(Integer.toString(result));
            } else if (instruction.startsWith("adi")) {
                int op1 = Integer.parseInt(stack.pop());
                int op2 = Integer.parseInt(stack.pop());
                int result = op1 + op2;
                stack.push(Integer.toString(result));
            } else if (instruction.startsWith("sbi")) {
                int op1 = Integer.parseInt(stack.pop());
                int op2 = Integer.parseInt(stack.pop());
                int result = op2 - op1;
                stack.push(Integer.toString(result));
            } else if (instruction.startsWith("sto")) {
                String valueToStore = stack.pop();
                int addressToStore = Integer.parseInt(stack.pop());
                memory[addressToStore] = valueToStore;
            } else if (instruction.startsWith("grt")) {
                int op1 = Integer.parseInt(stack.pop());
                int op2 = Integer.parseInt(stack.pop());
                boolean result = op2 > op1;
                stack.push(Boolean.toString(result));
            } else if (instruction.startsWith("let")) {
                int op1 = Integer.parseInt(stack.pop());
                int op2 = Integer.parseInt(stack.pop());
                boolean result = op2 < op1;
                stack.push(Boolean.toString(result));
            } else if (instruction.startsWith("gte")) {
                int op1 = Integer.parseInt(stack.pop());
                int op2 = Integer.parseInt(stack.pop());
                boolean result = op2 >= op1;
                stack.push(Boolean.toString(result));
            } else if (instruction.startsWith("lte")) {
                int op1 = Integer.parseInt(stack.pop());
                int op2 = Integer.parseInt(stack.pop());
                boolean result = op2 <= op1;
                stack.push(Boolean.toString(result));
            } else if (instruction.startsWith("equ")) {
                int op1 = Integer.parseInt(stack.pop());
                int op2 = Integer.parseInt(stack.pop());
                boolean result = op1 == op2;
                stack.push(Boolean.toString(result));
            } else if (instruction.startsWith("neq")) {
                int op1 = Integer.parseInt(stack.pop());
                int op2 = Integer.parseInt(stack.pop());
                boolean result = op1 != op2;
                stack.push(Boolean.toString(result));
            } else if (instruction.startsWith("and")) {
                boolean op1 = Boolean.parseBoolean(stack.pop());
                boolean op2 = Boolean.parseBoolean(stack.pop());
                boolean result = op1 && op2;
                stack.push(Boolean.toString(result));
            } else if (instruction.startsWith("or")) {
                boolean op1 = Boolean.parseBoolean(stack.pop());
                boolean op2 = Boolean.parseBoolean(stack.pop());
                boolean result = op1 || op2;
                stack.push(Boolean.toString(result));
            } else if (instruction.startsWith("lab")) {
                // do nothing
            } else if (instruction.startsWith("ujp")) {
                jumpTo(instructionValue);
            } else if (instruction.startsWith("fjp")) {
                boolean bValue = Boolean.parseBoolean(stack.pop());
                if (!bValue) {
                    jumpTo(instructionValue);
                }
            } else if (instruction.startsWith("stp")) {
                halt = true;
                return "Fim da execução!";
            }
        }
        return null;
    }

    private void jumpTo(String label) {
        for (int i = 0; i < instructions.size(); i++) {
            String peekInstruction = instructions.get(i);
            if (peekInstruction.startsWith("lab")) {
                String peekInstructionValue = getInstructionValue(peekInstruction);
                if (peekInstructionValue.equals(label)) {
                    currentInstruction = i + 1;
                    return;
                }
            }
        }
    }

    private String getInstructionValue(String instruction) {
        if (instruction.length() >= 4) {
            return instruction.substring(4);
        }
        return null;
    }

    public String getStackDescription() {
        String ret = "";
        for (String s : stack) {
            ret += s + "\n";
        }
        return ret;
    }

    public String[] getMemoryDescription() {
        return memory;
    }
}
