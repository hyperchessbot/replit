import re

req_fields = { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" }

match_year = re.compile("^[0-9]{4}$")
match_hair_color = re.compile("^#[0-9a-f]{6}$")
match_height = re.compile("^([1-9][0-9]*)(cm|in)$")

allowed_eye_colors = [ "amb", "blu", "brn", "gry", "grn", "hzl", "oth" ]

match_pid = re.compile("^[0-9]{9}$")

check_years = {
  "byr": [1920, 2002],
  "iyr": [2010, 2020],
  "eyr": [2020, 2030]
}

def check_year(year, check):
  return check[0] <= year <= check[1]

def is_field_valid(field):
  [key, value] = field.split(":")
  if key in check_years:
    if match_year.match(value) is None:
      print(f"{key} invalid syntax {value}")
      return False
    year = int(value)
    check = check_years[key]
    if not check_year(year, check):
      print(f"{key} {year} out of range {check}")
      return False
  if key == "hcl":
    if match_hair_color.match(value) is None:
      print(f"wrong hair color _{value}_")
      return False
  if key == "hgt":
    m = match_height.match(value)
    if m is None:
      print(f"wrong syntax for height {value}")
      return False
    [height, unit] = m.groups()
    if unit == "cm":
      if not ( 150 <= int(height) <= 193 ):
        print(f"height out of range {height} cm")
        return False
    elif unit == "in":
      if not ( 59 <= int(height) <= 76 ):
        print(f"height out of range {height} in")
        return False
    else:
      #this should not happen
      print(f"wrong height unit {unit}")
      return False
  if key == "ecl":
    if not ( value in allowed_eye_colors ):
      print(f"wrong eye color _{value}_")
      return False
  if key == "pid":
    if not match_pid.match(value):
      print(f"invalid syntax for pid _{value}_")
      return False
  return True

def is_valid(passport):  
  return not bool(req_fields.difference({item.split(":")[0] for item in filter(is_field_valid, re.split(" |\n", passport))}))

for input in ["inputcheck.txt"]:

  with open(input) as file:
    passports = file.read().split("\n\n")

  valid = len((list(filter(is_valid, passports))))

  print(f"{input} has {len(passports)} passports out of which {valid} are valid")