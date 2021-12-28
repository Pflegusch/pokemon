for attack_type in *.sql; do
    mysql -h 172.17.0.1 -P 3306 -uroot -proot12344 -D Pokemon -f < $attack_type
done