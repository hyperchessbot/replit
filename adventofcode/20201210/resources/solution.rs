use std::{env, fs};

fn outcast(records: &[usize], preamble: usize) -> usize {
    let mut pair_sums: Vec<usize> = vec![];
    for (idx, num1) in records[..preamble - 1].iter().enumerate() {
        for num2 in records[idx + 1..preamble].iter() {
            pair_sums.push(num1 + num2)
        }
    }

    let max_checks: usize = (preamble * (preamble - 1)) / 2;
    for (idx, num) in records[preamble..].iter().enumerate() {
        if pair_sums.contains(num) {
            let _ = pair_sums.drain(..preamble - 1);
            let (mut curr, mut shift) = (preamble - 2, preamble - 2);
            loop {
                pair_sums.insert(curr, records[idx + preamble - 1 - shift] + num);
                if curr == max_checks - 1 {
                    break;
                }
                curr += shift;
                shift -= 1;
            }
        } else {
            return *num;
        }
    }
    0
}

fn subsum(records: &[usize], target: usize) -> usize {
    let (mut start, mut total) = (0usize, 0usize);
    for (idx, num) in records.iter().enumerate() {
        total += num;

        while total > target {
            total -= records[start];
            start += 1;
        }
        if total == target {
            return records[start..=idx].iter().max().unwrap()
                + records[start..=idx].iter().min().unwrap();
        }
    }
    0
}

fn main() {
    let records: Vec<usize> = fs::read_to_string("./src/bin/day09/input")
        .unwrap()
        .split('\n')
        .map(|num| num.parse().unwrap())
        .collect();

    let target = outcast(&records, 25);
    let args: Vec<String> = env::args().collect();

    match args[1].as_str() {
        "1" => println!("{}", target),
        "2" => println!("{}", subsum(&records, target)),
        _ => println!("Only two puzzles daily!"),
    }
}
