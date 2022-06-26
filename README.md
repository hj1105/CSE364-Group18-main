# Content
- [Introduce](#introduce)
- [Main Hompage](#main-homepage)
- [Recommend by User information](#recommend-by-user-information)
- [Recommend by Movie information](#Recommend-by-Movie-information)

# Introduce
As a social animal, human will be in several group. Similar age, work place, watched movie, occupation, etc. These group can effect person's intersts. Using these information in movie recommendation system will make better recommendation for user.

In current movie recommendation algorithm recommend movie to user by using user's movie rated data, but our algorithm use user's information such as age, occupation, sexual to make better recommendation for users. On the internet, there are many group information such as youtube subscribe, facebook group, etc. So we can get these data easily. 
Currently, the data of users and movies comes from imdb.

# Main Homepage

You can use our application 'RecomME' by accessing http://localhost:8080 link.

If you move your mouse on a interesting movie poster, the movie poster would pop up and show its name and release year.

![1-1](https://user-images.githubusercontent.com/62790287/122660622-df481780-d1bd-11eb-858a-a9dac383ffe7.png)



( Seeing the movie Jumanji (1995) )

RecomME provides basically Top 10 movies from all genres and from each genre in the Homepage.

You can simply check which movies are recommended at a glance. If you just want to see some recommended movies, looking at the homepage is enough.

![1-2](https://user-images.githubusercontent.com/62790287/122660627-e3743500-d1bd-11eb-949b-48a572eae119.png)


(Top 10 movies from Action genre and from Animation genre)

# Recommend by User information

RecomME recommends you best Top 10 movies by user information

You can find best movies for you or other person using some information. 

For the service, clikc Recommend and By Users in Homepage. Or you can access http://localhost:8080/users.html

![2-1](https://user-images.githubusercontent.com/62790287/122660633-e7a05280-d1bd-11eb-9e50-242429c2831c.png)


There are 4 types of information about a User. Each type of information can be choosed using the bar provided by the application. 

Not all types of information need to be filled. you can choose what you want to fill. Especailly for genres, you can choice more than two. 

![2-2](https://user-images.githubusercontent.com/62790287/122660637-eb33d980-d1bd-11eb-9ff6-dc3497bc243a.png)


And then, RecomME will provide you Top 10 movies which the user would like.

![2-3](https://user-images.githubusercontent.com/62790287/122660640-ed963380-d1bd-11eb-8966-11ff66701f73.png)


# Recommend by Movie information

RecomME can recommend you movies based on a certain movie.

To use this service, clikc Recommend and By Movies in Homepage. Or you can access http://localhost:8080/movies.html

![3-1](https://user-images.githubusercontent.com/62790287/122660645-f0912400-d1bd-11eb-9755-6bab2e797267.png)


RecomME asks you three types of information about a movie. Title, release year, and limit.

You should fill some information about movie in the blank to find a certain movie.

Using the Title and release year, RecomME finds a movie.

The limit value decides how many movies you will get. If you leave it, the default value 10 would be in.

![3-2](https://user-images.githubusercontent.com/62790287/122660651-f424ab00-d1bd-11eb-8d75-ea368aa006a8.png)


After putting some information, RecomME will find a movie and recommends you movies with a number of limit.

![3-3](https://user-images.githubusercontent.com/62790287/122660655-f71f9b80-d1bd-11eb-956e-46f43b000332.png)




If you don't know the release year of the movie, it is okay. RecomME can only find movies with a Title. Year is optional. 

Also RecomME can find a movie even though you type a Title with only capital letters or small letters.

Just type a Title you are looking for. RecomME will find you the movie.

![3-3-1](https://user-images.githubusercontent.com/62790287/122660657-fa1a8c00-d1bd-11eb-8651-4a5abbd4b3c6.png)


( I find 'JUMANJI' and don't know the release year)

![3-3-2](https://user-images.githubusercontent.com/62790287/122660660-fd157c80-d1bd-11eb-8e67-829d3c6dc283.png)


( But RecomME will find you Jumanji (1995) and recommed you 10 movies )



You might want to choose one among movies which have same movie Title.

Then what to do? Just type the Title and leave the release year.

![3-4](https://user-images.githubusercontent.com/62790287/122660663-ff77d680-d1bd-11eb-8489-8499be0e9ad5.png)


( In this example, I will find a movie 'Les Misérables', but there are 'Les Misérables' movies more than 2. So I don't put value in Year )

Then our application will show you movies which have same Title before recommendation.

![3-5](https://user-images.githubusercontent.com/62790287/122660665-0272c700-d1be-11eb-90ce-3510121ff8bf.png)


( There are two 'Les Misérables'. I'll pick the one released in 1998 )

Choose one you want among them. And then recommended movies will come out.

![3-6](https://user-images.githubusercontent.com/62790287/122660667-056db780-d1be-11eb-9507-ed6c7edbf642.png)




If you don't type anything in the Title, there is nothing RecomME can do. In this case, RecomME will pop up a caution message to remind you to type a Title to find a movie

![a](https://user-images.githubusercontent.com/62790287/122673348-f8c38080-d20a-11eb-8dbe-5a4c880b1717.png)


Input value of Year must be 1900~2021(current year). If not, it shows error message.
![year_error](https://user-images.githubusercontent.com/62790287/122673695-a08d7e00-d20c-11eb-9268-b3ca6dac3ab1.png)


![year_error_2](https://user-images.githubusercontent.com/62790287/122673712-b438e480-d20c-11eb-81f8-bbea1e07c432.png)



RecomME can replace a capital letter to a small letter and vice versa. So RecomME can always find a movie.

However if you mis-type a Title or type a movie Title which is not in RecomME's database, there is no way for RecomME to find a movie what you want.

Then RecomME will say that he can't find that movie and ask if you want to search again or not. If you want, click 'Recommend Again'. Then the search page by movies will show up again.

![3-8](https://user-images.githubusercontent.com/62790287/122660670-0b639880-d1be-11eb-9576-2b107812001f.png)


![3-9](https://user-images.githubusercontent.com/62790287/122660673-128aa680-d1be-11eb-8cb9-2c65cc78677a.png)



( There is no movie whose title is UNIST stroy )



There is also another case. You might type the Title right, but mis-type the Year value. Or the movie you finding might not be in our database.

In that case, RecomME will notice the situation, and tell you that there is another movie which has same title but different release year.

And RecomME will show movies in its database which has different release year. If the movie is the one you find, then click it.

![3-10](https://user-images.githubusercontent.com/62790287/122660675-161e2d80-d1be-11eb-8f1c-7e7ab62152b5.png)


( I try to use a movie 'Men in Black' which was remaked in 2019 )

![3-11](https://user-images.githubusercontent.com/62790287/122660681-18808780-d1be-11eb-9087-986a991945d6.png)


( But the movie was not in the database. RecomME suggests you the other one released in 1997 )
