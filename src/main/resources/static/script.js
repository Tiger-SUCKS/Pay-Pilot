// ==========================================
// PayPilot Dashboard
// ==========================================


// ==========================================
// Load Analytics
// ==========================================

async function loadAnalytics() {

    try {

        const response = await fetch("/api/payments/analytics");

        if (!response.ok) {
            throw new Error("Failed to load analytics");
        }

        const data = await response.json();

        // Total payments
        document.getElementById("totalPayments").textContent =
            data.totalPayments;

        // Successful payments
        document.getElementById("successfulPayments").textContent =
            data.successfulPayments;

        // Failed payments
        document.getElementById("failedPayments").textContent =
            data.failedPayments;

        // Failure rate
        document.getElementById("failureRate").textContent =
            data.failureRate.toFixed(2) + "%";

    } catch (error) {

        console.error("Analytics error:", error);

    }
}


// ==========================================
// Load Payments
// ==========================================

async function loadPayments() {

    try {

        const response = await fetch("/api/payments");

        if (!response.ok) {
            throw new Error("Failed to load payments");
        }

        const payments = await response.json();

        const table = document.getElementById("paymentTable");

        // Clear existing table rows
        table.innerHTML = "";


        // Loop through payments
        for (const payment of payments) {

            let recommendation = "-";


            // Get recommendation only for failed payments
            if (payment.status === "FAILED") {

                try {

                    const recommendationResponse =
                        await fetch(
                            `/api/payments/${payment.id}/recommendation`
                        );

                    if (recommendationResponse.ok) {

                        recommendation =
                            await recommendationResponse.text();

                    } else {

                        recommendation =
                            "Unable to load recommendation.";
                    }

                } catch (error) {

                    console.error(
                        "Recommendation error:",
                        error
                    );

                    recommendation =
                        "Unable to load recommendation.";
                }
            }


            // Create table row
            const row = document.createElement("tr");


            // Create row HTML
            row.innerHTML = `
                
                <td>
                    ${payment.id}
                </td>

                <td>
                    ₹${payment.amount}
                </td>

                <td>
                    ${payment.paymentMethod}
                </td>

                <td>
                    <span class="status ${payment.status.toLowerCase()}">
                        ${payment.status}
                    </span>
                </td>

                <td>
                    ${payment.failureReason || "-"}
                </td>

                <td>
                    ${recommendation}
                </td>

            `;


            // Add row to table
            table.appendChild(row);
        }

    } catch (error) {

        console.error("Payment error:", error);

    }
}


// ==========================================
// Load Dashboard
// ==========================================

async function loadDashboard() {

    await loadAnalytics();

    await loadPayments();

}


// ==========================================
// Start Dashboard
// ==========================================

loadDashboard();