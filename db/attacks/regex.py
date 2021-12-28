import re

sql = ""

def find_attacks(attack, str):
    return re.findall('class="{}atk">([^<]+)'.format(attack), str)

if __name__ == '__main__':
    attacks = ["fire", "grass", "water", "ground", "poison", 
        "normal", "flying", "psychic", "dark", "bug", "fighting", 
        "ice", "steel", "electric", "rock", "ghost", "dragon", "fairy"]

    with open("attacks.php") as db:
        lines = str(db.readlines())
        for type in attacks:
            attacks = find_attacks(type, lines)

            for match in attacks:
                sql += "INSERT INTO Attacks VALUES ('{}', '{}', 1, 1, 1, 0);\n".format(match, type.capitalize())
            
            with open("{}.sql".format(type), "w+") as file:
                file.write(sql)
            
            sql = ""