SBT := sbt
CS  := cs
CLASS := Main
ROOT := src/main/scala
SRC := $(ROOT)/Main.scala $(ROOT)/utils/KeyUtils.scala

default: run

ensure-sbt:
	@echo "🔍 verifying sbt..."
	@if command -v $(SBT) >/dev/null 2>&1; then \
		echo "✅ sbt is installed"; \
	elif command -v $(CS) >/dev/null 2>&1; then \
		echo "⬇️  sbt not found, CS install"; \
		$(CS) install sbt; \
	elif command -v apt >/dev/null 2>&1; then \
    		echo "⬇️  Installation of Coursier"; \
    		sudo apt update && sudo apt install -y curl gzip; \
    		curl -fL https://github.com/coursier/coursier/releases/latest/download/cs-x86_64-pc-linux.gz \
    		  | gzip -d > $(CS) && chmod +x $(CS) && sudo mv $(CS) /usr/local/bin/cs; \
    		$(CS) install sbt; \
	else \
		echo "❌ Failed to install sbt automatically"; \
		exit 1; \
	fi

$(CLASS): $(SRC) ensure-sbt
	@echo "🔨 Compiling ft_ality..."
	@sbt -warn compile

run: $(CLASS)
	@echo "🚀 Running ft_ality"
	@sbt -warn -batch run

clean:
	@echo "🧹 Cleaned $(shell ls *.class 2>/dev/null)"
	@sbt clean
	@rm -f *.class

scala:
	@scalac3 $(SRC)
	@scala $(CLASS)

re: clean run