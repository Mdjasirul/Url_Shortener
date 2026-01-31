export async function shortenUrlApi(data) {
  const response = await fetch("http://localhost:8080/shorten", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw new Error("Failed to shorten URL");
  }

  return response.text();
}
