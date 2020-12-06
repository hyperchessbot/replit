use std::collections::HashSet;

fn main() {
    let records = std::fs::read_to_string("../input.txt").unwrap();
    let records = records.split("\n\n").map(|group| {
        group
            .split('\n')
            .map(|person| person.chars().collect::<HashSet<char>>())
    });

    let mut output = 0usize;
    let args: Vec<String> = std::env::args().collect();

    for mut group in records {
        let start: HashSet<char> = group.next().unwrap();
        let finish = match args[1].as_str() {
            "1" => group.fold(start, |acc, x| (&acc | &x)),
            "2" => group.fold(start, |acc, x| (&acc & &x)),
            _ => {
                println!("Only two puzzles daily!");
                return;
            }
        };
        output += finish.len();
    }
    println!("{}", output);
}