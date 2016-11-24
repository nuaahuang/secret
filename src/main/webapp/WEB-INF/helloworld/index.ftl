<!DOCTYPE html>
<html>
<header>
</header>
<body>
<h3>hello world! --${myname}</h3>
<img src="http://photo.enterdesk.com/2010-10-24/enterdesk.com-3B11711A460036C51C19F87E7064FE9D.jpg" />
<script type="text/javascript" src="../js/html2canvas.js"></script>
<script type="text/javascript">
    html2canvas(document.body, {
    	proxy:"/Me/helloworld/proxy/image"
    }).then(function(canvas) {
        document.body.appendChild(canvas);
    });
</script>
</body>
</html>