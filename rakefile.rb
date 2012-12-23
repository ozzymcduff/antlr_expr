require 'fileutils'
require 'rake'
@standard_classpaths = [#"%CLASSPATH%",
	"./lib/antlr-3.4-complete-no-antlrv2.jar",
	"./lib/junit-4.11.jar"]
def classpath
	return @standard_classpaths.join(File::PATH_SEPARATOR)
end
def classpath_and_bin
	return (@standard_classpaths+['./bin']).join(File::PATH_SEPARATOR)
end 
task :defualt=>[:build]
#puts "File::SEPARATOR: #{File::SEPARATOR}"
#puts "File::SEPARATOR: #{File::PATH_SEPARATOR}"
desc "compile"
task :build do
	sh "javac -cp #{classpath} ./*.java -d ./bin"
end

task :bin do
	FileUtils.mkdir_p 'bin'
end

task :run=>[:build] do
	sh "java -classpath #{classpath_and_bin} Main input.txt"
end

task :test=>[:build] do
	sh "java -classpath #{classpath_and_bin} org.junit.runner.JUnitCore TestCases"
end

task :clean do
	cd "bin" do
		FileUtils.rm Dir.glob("*.class")
	end
end

def render_grammar(grammar)
	sh "java -cp #{classpath_and_bin} org.antlr.Tool #{grammar} -o ./"
end

task :evalgrammar => [:exprgrammar] do
	render_grammar("Eval.g")
end

task :exprgrammar do
	render_grammar("Expr.g")
end