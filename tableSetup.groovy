#!/usr/bin/env groovy
@Grapes([
        @Grab(group = 'org.xerial', module = 'sqlite-jdbc', version = '3.7.2'),
        @GrabConfig(systemClassLoader = true)
])
import groovy.sql.Sql

def sql = Sql.newInstance("jdbc:sqlite:interviewQA.sqlite", "org.sqlite.JDBC")

sql.execute("drop table if exists comment")
sql.execute("create table comment (id integer primary key autoincrement, name string, email string, comment string)")
       
def comments = sql.dataSet("comment")
comments.add(name:'chi wai chan', email:'wai_chi_chan@yahoo.com.hk',comment:'some comment') 
        
sql.eachRow("select * from comment") { 
  println("id=${it.id}, name= ${it.name}")
}

