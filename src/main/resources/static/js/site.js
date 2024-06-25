var header = document.querySelector('.header');
var origOffsetY = header.offsetTop;

function onScroll(e) {
    if (document.body.scrollTop > 80 || document.documentElement.scrollTop > 80) {
        window.scrollY >= origOffsetY ? header.classList.add('sticky') :
            header.classList.remove('sticky');
    }
    else {
        header.classList.remove('sticky');
    }
}

document.addEventListener('scroll', onScroll);