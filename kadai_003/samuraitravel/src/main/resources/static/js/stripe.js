const stripe = Stripe('pk_test_51PUo1Y06Jq3kkCxJeGWywSW6FsOy4Umc2YTxRYLj5DvHTDxGlUdh7gklR51UDjxOlSw6eT8l2Z2qECWITbNWQZUU00KqzduPgw');
 const paymentButton = document.querySelector('#paymentButton');
 
 paymentButton.addEventListener('click', () => {
   stripe.redirectToCheckout({
     sessionId: sessionId
   })
 });