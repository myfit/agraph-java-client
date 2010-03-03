(defproject clj-allegrograph "1.0.0-SNAPSHOT"
  :description "The AllegroGraph client interface"
  :url "http://github.com/franzinc/agraph-java-client/"
  :source-path "src/clj"
  :java-source-path "src/java"
  :javac-fork "true"
  :dependencies [[org.clojure/clojure "1.1.0-master-SNAPSHOT"]
                 [org.clojure/clojure-contrib "1.0-SNAPSHOT"]]
  :dev-dependencies [[lein-clojars "0.5.0-SNAPSHOT"]
                     [lein-javac "0.0.2-SNAPSHOT"]])