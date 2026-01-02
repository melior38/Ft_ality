SBT := sbt
CS  := cs

.PHONY: help about run ensure-sbt

help:
	@echo "Targets available :"
	@echo "  make about        -> Verify if sbt installed, install it if missing and run 'sbt about'"
	@echo "  make run          -> Verify if sbt installed, install it if missing and run Ft_ality (later)"

ensure-sbt:
	@echo "🔍 verifying sbt..."
	@if command -v $(SBT) >/dev/null 2>&1; then \
		echo "✅ sbt is installed"; \
	elif command -v $(CS) >/dev/null 2>&1; then \
		echo "⬇️  sbt not found, CS install"; \
		$(CS) install sbt; \
	elif command -v apt >/dev/null 2>&1; then \
    		echo "⬇️  Installation de Coursier"; \
    		sudo apt update && sudo apt install -y curl gzip; \
    		curl -fL https://github.com/coursier/coursier/releases/latest/download/cs-x86_64-pc-linux.gz \
    		  | gzip -d > $(CS) && chmod +x $(CS) && sudo mv $(CS) /usr/local/bin/cs; \
    		$(CS) install sbt; \
	else \
		echo "❌ Failed to install sbt automatically"; \
		exit 1; \
	fi

about: ensure-sbt
	@echo "ℹ️  Informations about build :"
	@$(SBT) about

run: ensure-sbt
	@echo "🚀 Running Ft_ality"
	@$(SBT) run