/**
 *
 */

 //first create request for server to create order

 const buyPaymentStart = () => {

	   console.log("payment started..........");
	   let amount = $("#totalprice").val();
	   let rest = $("#restorentid").val();
	   console.log(amount);
	   console.log(rest);



   //from here we're sending request to the server
   $.ajax(
     {
     url:'/buyer',
     data:JSON.stringify({amount:amount,info:'order_request'}),
     contentType:'application/json',
     type:'POST',
     dataType:'json',
     success:function(response){
       //invoked where success
       console.log(response)
       if(response.status == "created"){
         //open payment form
         let options={
           key:'rzp_test_Sp0TxZ70PsKMQg',
           amount: response.amount,
           currency:'INR',
           name:'online-chatori-chaat',
           description:'Pay for order',
           image:'https://mir-s3-cdn-cf.behance.net/projects/404/c3a1e861370751.Y3JvcCw5MTgsNzE4LDM5OCwyNjA.jpg',
           order_id: response.id,

           handler:function(response){
             console.log(response.razorpay_payment_id);
             console.log(response.razorpay_order_id);
             console.log(response.razorpay_signature);
             console.log("payment successfull!! ");
            // swal("Payment Done!", "congrates !! Your Order placed!!!", "success");
             alert("Your Order Das Been Placed Successfully!!!!!!!");
             window.location.href="MyOrder";
           },
           prefill: {
           name: "",
           email: "",
           contact: "",
          },

          notes: {
           address: "online-chatori-chaat palasiya ",
         },

         theme: {
           "color": "#3399cc",
         },
       };

       let rzp = new Razorpay(options);
       rzp.on('payment.failed', function (response){
             console.log(response.error.description);
             console.log(response.error.source);
             console.log(response.error.code);
             console.log(response.error.step);
             console.log(response.error.reason);
             console.log(response.error.metadata.order_id);
             console.log(response.error.metadata.payment_id);
             // alert("Ops payment failed");
             swal("Something missMatched!", "Ops payment failed", "error");
         });
        rzp.open();
       }
     },
     error:function(error){
       //involked when console.error(console);
       console.log(error)
       alert("something went wrogn !!")
     },
    });
 
  };
