package net.stiekema.jeroen.aoc2020;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day08 {

    public static void main(String[] args) {
        InputStream in = Day08.class.getResourceAsStream("/day8.txt");
        List<Instruction> lines = new BufferedReader(new InputStreamReader(in)).lines()
                .filter(StringUtils::isNotEmpty)
                .map(Instruction::new)
                .collect(Collectors.toList());

        Day08 day08 = new Day08();

        System.out.println("Part 1: " + day08.getAnswerPart1(lines));
        System.out.println("Part 2: " + day08.getAnswerPart2(lines));
    }

    private String getAnswerPart1(List<Instruction> instructions) {
        Execution execution = new Execution();
        execution.execute(instructions);
        return "" + execution.context.accumulator;
    }

    private String getAnswerPart2(List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            if (instruction.isJmpOrNop()) {
                instruction.flip();
                Execution execution = new Execution();
                if (execution.execute(instructions) == 0) {
                    return "" + execution.context.accumulator;
                }
                instruction.flip();
            }
        }
        return "No successful execution found";
    }
}

class Execution {
    final Context context;

    Execution() {
        this.context = new Context();
    }

    int execute(List<Instruction> instructions) {
        Set<Integer> addresses = new HashSet<>();
        while (true) {
            if (!addresses.add(context.address)) {
                return -1;
            }
            instructions.get(context.address).execute(context);
            if (context.address == instructions.size()) {
                return 0;
            }
        }
    }
}

class Context {
    int address = 0;
    int accumulator = 0;

    public Context() {
    }
}

class Instruction {
    private String operation;
    private final int argument;

    Instruction(String line) {
        String[] splits = line.split(" ");
        this.operation = splits[0];
        this.argument = Integer.parseInt(splits[1]);
    }

    void execute(Context context) {
        if (operation.equals("jmp")) {
            context.address += argument;
        } else if (operation.equals("acc")) {
            context.accumulator += argument;
            context.address++;
        } else if (operation.equals("nop")) {
            context.address++;
        }
    }

    void flip() {
        if (operation.equals("jmp")) {
            operation = "nop";
        } else if (operation.equals("nop")) {
            operation = "jmp";
        }
    }

    boolean isJmpOrNop() {
        return operation.equals("jmp") || operation.equals("nop");
    }

    public String toString() {
        return operation + " " + argument;
    }

}
