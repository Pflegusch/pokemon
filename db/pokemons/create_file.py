import re

if __name__ == '__main__':
    with open("pokemons.txt") as db:
        lines = db.readlines()
        sql = ""
        for pokemon in lines:
            pokemon = pokemon.rstrip()
            sql += "INSERT INTO Pokemons VALUES (NULL, '{}', 1, 1, 1, 'TODO', 'TODO', 'PLACEHOLDER', 'PLACEHOLDER', 'PLACEHOLDER', 'PLACEHOLDER', NULL);\n".format(pokemon)
        
        with open("pokemons.sql".format(type), "w+") as file:
            file.write(sql)
            