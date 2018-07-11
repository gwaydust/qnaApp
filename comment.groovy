package ratpack.example.books

@Grapes([
        @Grab(group = 'org.xerial', module = 'sqlite-jdbc', version = '3.7.2'),
        @GrabConfig(systemClassLoader = true)
])
import groovy.sql.Sql

//Class.forName("org.sqlite.JDBC")

class Comment {

    def sql = Sql.newInstance("jdbc:sqlite:sample.db", "org.sqlite.JDBC")
 
    Comment() {
        sql.execute("drop table if exists comment")
        sql.execute("create table comment (email string, name string, comment string)")
        def comments = sql.dataSet("comment")
        people.add(email:'wai_chi_chan@yahoo.com.hk',name:'chi wai chan',comment:'haha hoho')
    }

    void getByEmail(final string email) {
        sql.eachRow("select * from comments where email like '{$email}") {
            println("id=${it.id}, name= ${it.name}")
        }
    }
}