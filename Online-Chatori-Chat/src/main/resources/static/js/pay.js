/**
 *
 */

 // first request to server to create Order

 const paymentStart = () => {

    console.log("payment started...");
    var amount = document.getElementById('payment_field').value;
    console.log(amount); 


 //code
 //from here we are send request to server

$.ajax({
  url:'/create_order',
  data:JSON.stringify({amount:amount,info:'order_request'}),
  contentType:'application/json',
  type:'POST',
  success:function(response){
    //invoked where success
    
    if(response == "created"){
      //open payment form
      console.log("new user")
      let options={
        key:'rzp_test_Sp0TxZ70PsKMQg',
        amount: 1*100,
        currency:'INR',
        name:'Food For Foody',
        description:'Pay for order',
        image:'https://mir-s3-cdn-cf.behance.net/projects/404/c3a1e861370751.Y3JvcCw5MTgsNzE4LDM5OCwyNjA.jpg',
        order_id: response.id,

        handler:function(response){
	
         // console.log(response.razorpay_payment_id);
         // console.log(response.razorpay_order_id);
         // console.log(response.razorpay_signature);
          console.log("payment successfull!! ");
          
         // swal("Payment Done!", "congrates !! Payment successfull !!!", "success");
          alert("you have successfully purchase our plan ,Now you can enjoy our services!!!!!!!");
	      window.location.href="dashboard";
          
        },
        prefill: {
        name: "",
        email: "",
        contact: "",
       },

       notes: {
        address: "aakash bhaba footfor foody ",
      },

      theme: {
        "color": "#3399cc",
      },
    };

    let rzp = new Razorpay(options);
    rzp.on('payment.failed', function (response){
        /*  console.log(response.error.description);
          console.log(response.error.source);
          console.log(response.error.code);
          console.log(response.error.step);
          console.log(response.error.reason);
          console.log(response.error.metadata.order_id);
          console.log(response.error.metadata.payment_id);*/
          // alert("Ops payment failed");
          swal("Something missMatched!", "Ops payment failed", "error");
      });
     rzp.open();
    }  
    else{
	
	console.log("already user")
      let options={
        key:'rzp_test_Sp0TxZ70PsKMQg',
        amount1: 1*100,
        currency:'INR',
        name:'Food For Foody',
        description:'Pay for order',
        image:'https://mir-s3-cdn-cf.behance.net/projects/404/c3a1e861370751.Y3JvcCw5MTgsNzE4LDM5OCwyNjA.jpg',
        order_id: response.id,

        handler:function(response){
	
          /*console.log(response.razorpay_payment_id);
          console.log(response.razorpay_order_id);
          console.log(response.razorpay_signature);*/
          console.log(" payment successfull!! ");
          
         // swal("Payment Done!", "congrates !! Payment successfull !!!", "success");
	        
	        alert("You have successfully Renew your plan, Now you can enjoy our services!!!!!!!");
            window.location.href="renewexpireplan";     
        },
        prefill: {
        name: "",
        email: "",
        contact: "",
       },

       notes: {
        address: "aakash bhaba footfor foody ",
      },

      theme: {
        "color": "#3399cc",
      },
    };

    let rzp = new Razorpay(options);
    rzp.on('payment.failed', function (response){
          /*console.log(response.error.description);
          console.log(response.error.source);
          console.log(response.error.code);
          console.log(response.error.step);
          console.log(response.error.reason);
          console.log(response.error.metadata.order_id);
          console.log(response.error.metadata.payment_id);*/
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
