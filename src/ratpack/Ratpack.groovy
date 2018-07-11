#!/usr/bin/env groovy
@Grapes([
	@Grab(group='io.ratpack', module='ratpack-groovy', version='0.9.6'),
    @Grab(group = 'org.xerial', module = 'sqlite-jdbc', version = '3.7.2'),
    @GrabConfig(systemClassLoader = true)
])

import groovy.sql.Sql
import static ratpack.groovy.Groovy.*

//import ratpack.form.Form
def sql = Sql.newInstance("jdbc:sqlite:interviewQA.sqlite", "org.sqlite.JDBC")
def userName = ''
def userEmail = ''
def userComment = ''
def comments = sql.dataSet("comment")
def questionAnswers = sql.dataSet("questionAnswers")

// You can change anything in the ratpack {} closure without needing to restart
ratpack {
    bindings {
    }
    
    handlers {
        get {
            //response.send "This is the app root (also try: /date and /some.txt)"
            render groovyTemplate("index.html",subject:"Java Interview Q and A")
        }

        get("index") {
            render groovyTemplate("index.html",subject:"Java Interview Q and A")
        }

        get("fulllist") {
            render groovyTemplate("fulllist.html", questionAnswers: questionAnswers, subject:"Questions and Answers")
        }

        get("date") {
            render groovyTemplate("date.html")
        }
        
        get("firefoxInstall") {
            //render groovyTemplate("firefoxInstall.html")
            render groovyTemplate("manifest.webapp")
        }
                        
        post("comment") {
            def body = request.getBody().getText()
            body.eachLine { line ->
                if (line[0..4] == 'name=' ) {
                    userName = line.substring(5)
                }
                else if (line[0..5] == 'email=' ) {
                    userEmail = line.substring(6)                                        
                }
                else if (line[0..7] == 'message=' ) {
                    userComment = line.substring(8)
                }
                else {
                    userComment += line
                }                                
            }
            comments.add(name:userName, email:userEmail,comment:userComment) 
             //Form form = parse(Form.class)
                
            render groovyTemplate("message.html",msg:"Thank you for your comment, ${userName}. Will look into it.", subject:"Message")
        }                          
        
        assets "public"
    }
}
