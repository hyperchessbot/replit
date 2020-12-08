use std::collections::HashSet;
use std::convert::TryInto;
use std::{env, fs};

fn run_boot(instructions: &[(&str, isize)], mark: usize) -> (isize, bool) {
    let mut acc = 0isize;
    let mut completed = false;
    let mut visited: HashSet<usize> = HashSet::new();
    let mut current = 0usize;

    while !visited.contains(&current) {
        visited.insert(current);
        let (operation, agrument) = instructions[current];
        if operation == "acc" {
            acc += agrument;
            current += 1;
        } else if (operation == "nop" && mark != current) || (operation == "jmp" && mark == current)
        {
            current += 1;
        } else {
            current = (current as isize + agrument)
                .try_into()
                .unwrap_or(instructions.len());
        }

        if !((..instructions.len()).contains(&current)) {
            break;
        }

        if current == instructions.len() - 1 {
            if instructions[current].0 == "acc" {
                acc += instructions[current].1;
            }
            completed = true;
            break;
        }
    }
    (acc, completed)
}

fn main() {
    let records = fs::read_to_string("./src/bin/day08/input").unwrap();
    let mut instructions: Vec<(&str, isize)> = vec![];
    for record in records.split('\n') {
        if let [operation, argument, ..] = record.split(' ').collect::<Vec<&str>>()[..] {
            instructions.push((operation, argument.parse::<isize>().unwrap()))
        };
    }

    let args: Vec<String> = env::args().collect();

    match args[1].as_str() {
        "1" => println!("{}", run_boot(&instructions, instructions.len()).0),
        "2" => {
            for mark in 0..instructions.len() {
                let (acc, completed) = run_boot(&instructions, mark);
                if completed {
                    println!("{}", acc);
                    break;
                }
            }
        }
        _ => println!("Only two puzzles daily!"),
    }
}
