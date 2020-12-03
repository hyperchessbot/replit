for input in ["inputsmall.txt", "input.txt"]:

  print(input)

  with open(input) as file:
    lines = file.read().split("\n")

  class Vect:
    def __init__(self, x, y):
      self.x = x
      self.y = y

    def __mul__(self, op):
      return Vect(self.x * op, self.y * op)

    def __repr__(self):
      return f"Vect({self.x}, {self.y})"

  deltas = [
    Vect(1, 1),
    Vect(3, 1),
    Vect(5, 1),
    Vect(7, 1),
    Vect(1, 2)
  ]

  prod = 1

  for delta in deltas:
    hit_tree = 0

    for i in range((len(lines) - 1) // delta.y + 1):
      pos = delta * i

      line = lines[pos.y]
    
      hit_tree += int(line[pos.x % len(line)] == "#")

    prod *= hit_tree

    print(f"{delta} {hit_tree:4d} {prod}")

