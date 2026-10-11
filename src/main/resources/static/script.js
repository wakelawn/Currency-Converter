
const apiKeyInput = document.getElementById("apiKey");
const amountInput = document.getElementById("amount");
const fromCurrency = document.getElementById("fromCurrency");
const toCurrency = document.getElementById("toCurrency");
const convertButton = document.getElementById("convertButton");
const swapButton = document.getElementById("swapButton");
const message = document.getElementById("message");
const result = document.getElementById("result");
const convertedAmount = document.getElementById("convertedAmount");
const conversionDetails = document.getElementById("conversionDetails");
const historyButton = document.getElementById("historyButton");
const historyList = document.getElementById("historyList");

async function getErrorMessage(response) {
    try {
        const data = await response.json();
        return data.error || Object.values(data).join(", ") || "Request failed.";
    } catch {
        return `Request failed with status ${response.status}.`;
    }
}

// Convert currency.
convertButton.addEventListener("click", async function() {
    const apiKey = apiKeyInput.value.trim();
    const amount = Number(amountInput.value);

    if (!apiKey) {
        message.textContent = "Please enter your API key.";
        result.hidden = true;
        return;
    }

    if (!Number.isFinite(amount) || amount <= 0) {
        message.textContent = "Please enter an amount greater than zero.";
        result.hidden = true;
        return;
    }

    message.textContent = "Converting...";
    result.hidden = true;
    convertButton.disabled = true;

    try {
        const response = await fetch("/api/currency/convert", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-API-KEY": apiKey
            },
            body: JSON.stringify({
                from: fromCurrency.value,
                to: toCurrency.value,
                amount: amount
            })
        });

        if (!response.ok) {
            throw new Error(await getErrorMessage(response));
        }

        const data = await response.json();

        convertedAmount.textContent =
            `${data.convertedAmount} ${data.to}`;

        conversionDetails.textContent =
            `${data.amount} ${data.from} × rate ${data.rate}`;

        result.hidden = false;
        message.textContent = "Conversion successful!";

        // Refresh recent conversions.
        await loadHistory();

    } catch (error) {
        message.textContent = error.message || "Something went wrong.";
    } finally {
        convertButton.disabled = false;
    }
});

// Swap the selected currencies.
swapButton.addEventListener("click", function() {
    const previousCurrency = fromCurrency.value;

    fromCurrency.value = toCurrency.value;
    toCurrency.value = previousCurrency;
});

// Load recent conversion history.
async function loadHistory() {
    const apiKey = apiKeyInput.value.trim();

    if (!apiKey) {
        historyList.textContent = "Enter your API key to load history.";
        return;
    }

    historyList.textContent = "Loading history...";
    historyButton.disabled = true;

    try {
        const response = await fetch("/api/currency/history", {
            headers: {
                "X-API-KEY": apiKey
            }
        });

        if (!response.ok) {
            throw new Error(await getErrorMessage(response));
        }

        const history = await response.json();

        historyList.replaceChildren();

        if (history.length === 0) {
            historyList.textContent = "No conversions yet.";
            return;
        }

        // Show the five most recent records.
        history.slice().reverse().slice(0, 5).forEach(function(item) {
            const entry = document.createElement("div");
            entry.className = "history-item";

            const summary = document.createElement("p");
            summary.textContent =
                `${item.amount} ${item.fromCurrency} → ` +
                `${item.convertedAmount} ${item.toCurrency}`;

            const date = document.createElement("small");
            date.textContent = item.createdAt
                ? `Date: ${Array.isArray(item.createdAt)
                    ? item.createdAt.slice(0, 3).join("-")
                    : item.createdAt}`
                : "";

            entry.append(summary, date);
            historyList.appendChild(entry);
        });

    } catch (error) {
        historyList.textContent =
            error.message || "Could not load conversion history.";
    } finally {
        historyButton.disabled = false;
    }
}

historyButton.addEventListener("click", loadHistory);
