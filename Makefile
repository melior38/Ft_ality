SBT := sbt
CS  := cs
JAR := target/scala-3.3.7/ft_ality_3-0.1.0-SNAPSHOT.jar

default: run

ensure-sbt:
	@echo "🔍 verifying sbt..."
	if command -v $(SBT) >/dev/null 2>&1; then \
		echo "✅ sbt is installed"; \
	elif command -v $(CS) >/dev/null 2>&1; then \
		echo "⬇️  sbt not found, CS install"; \
		$(CS) install sbt; \
		$(CS) install scala3; \
	elif command -v apt >/dev/null 2>&1; then \
    		echo "⬇️  Installation of Coursier"; \
    		sudo apt update && sudo apt install -y curl gzip; \
    		curl -fL https://github.com/coursier/coursier/releases/latest/download/cs-x86_64-pc-linux.gz \
    		  | gzip -d > $(CS) && chmod +x $(CS) && sudo mv $(CS) /usr/local/bin/cs; \
    		$(CS) install sbt; \
    		grep -qxF 'export PATH=$PATH:/home/lord/.local/share/coursier/bin' ~/.zshrc \
    		|| echo 'export PATH=$PATH:/home/lord/.local/share/coursier/bin' >> ~/.zshrc; \
			$(CS) install scala3; \
	else \
		echo "❌ Failed to install sbt and scala3 automatically"; \
		exit 1; \
	fi

package: $(SRC) ensure-sbt
	@echo "🔨 Compiling ft_ality..."
	sbt package

run: package
	@echo "🚀 Running ft_ality"
	scala3 $(JAR)


clean:
	@echo "🧹 Cleaned $(shell ls *.class 2>/dev/null)"
	@sbt clean
	@rm -f *.class

re: clean run