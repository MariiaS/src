//Выполнила Солодянкина Мария, группа 3743

//При загрузке страницы на ней размещаются 20 цветных прямоугольников случайных размеров и цветов (все в пределах некоторой выделенной прямоугольной области). Например, так, как это показано на картинке:
 
//Пользователь играет в следующую игру: он щелкает мышью по видимым частям прямоугольников. При щелчке мышью на прямоугольнике он становится “активным”, визуально это выражается в том, что прямоугольник изображается на переднем плане, и граница прямоугольника становится “толстой”. Далее “активный” прямоугольник можно передвинуть, используя для этого клавиши “вверх”. “вниз”, “вправо” и “влево” (передвигают прямоугольник на одну точку в соответствующем направлении) или “Ctrl-вверх”. “ Ctrl-вниз”, “ Ctrl-вправо” и “ Ctrl-влево” (передвигают прямоугольник к границе игрового поля). 
//Сдвигать прямоугольники за границы игрового поля нельзя. Игра заканчивается, когда прямоугольники больше не перекрывают друг друга (границы прямоугольников можно не учитывать, то есть прямоугольники, перекрывающиеся только по границам, считаются неперекрывающимися). Цель игры - “раздвинуть” прямоугольники за наименьшее время. После окончания игры в окне выдается статистика: за какое время, начиная с первого щелчка, удалось сделать прямоугольники неперекрывающимися.

//Просьба в решении не использовать сторонних библиотек Javascript, таких, как, например, jQuery.



var container = document.getElementById("container");
var width = 1000;
var height = 600;
var rectangles = [];
var active;
var startCounter;
//создаем прямоугольники разных размеров и цветов 
// они не могут выходить за нашу область
for (var i = 0; i < 20; i++) {
	var rectangle = document.createElement("div");
	var rect_height = rand(10, 300),
		rect_width = rand(30, 400);
	rectangle.style.height = rect_height + "px";
	rectangle.style.width = rect_width + "px";
	rectangle.style.top = rand(0, height - rect_height) + "px";
	rectangle.style.left = rand(0, width - rect_width) + "px";
	rectangle.style.background = "rgb(" + rand(0, 255) + "," + rand(0, 255) + "," + rand(0, 255) + ")";

	rectangles.push(rectangle);
//при кликанье мышкой прямоугольник выделяется

	rectangle.addEventListener("click", function (event) {
		if (!startCounter) {
			startCounter = new Date();
		}

		if (active) {
			active.className = "";
		}
		active = event.target;
		active.className = "active";
	});

	container.appendChild(rectangle);
}
//задаем значения стрелкам на клавиатуре

var leftKey = 37;
var upKey = 38;
var rightKey = 39;
var downKey = 40;

//задаем работу стрелкам: они перемещают прямоугольники на пиксель 
//в нужном направлении, также не дают выйти ему за пределы нашей области

window.addEventListener("keydown", function (event) {
	if (!active) {
		return;
	}

	var top = parseInt(active.style.top);
	var left = parseInt(active.style.left);

	if (event.ctrlKey) {
		switch (event.keyCode) {
			case upKey: top = 0; break;
			case leftKey: left = 0; break;
			case rightKey: left = width; break;
			case downKey: top = height; break;
		}
	}
	else {
		switch (event.keyCode) {
			case upKey: top--; break;
			case downKey: top++; break;
			case rightKey: left++; break;
			case leftKey: left--; break;
		}
	}

	if (top < 0) {
		active.style.top = 0;
	}
	else if (top > height - parseInt(active.style.height)) {
		active.style.top = (height - parseInt(active.style.height) - 1) + "px"
	}
	else {
		active.style.top = top + "px";
	}

	console.log(top, left);

	if (left < 0) {
		active.style.left = 0;
	}
	else if (left > width - parseInt(active.style.width)) {
		active.style.left = (width - parseInt(active.style.width) - 1) + "px"
	}
	else {
		active.style.left = left + "px";
	}

	checkVictory();
});

//для удобства задаем функцию рандома с..до, чтобы не прописывать каждый раз

function rand (from, to) {
	return Math.round(from+Math.random()*(to - from));
}
//проверяем, ересекаются ли прямоугольники. Просматриваем варианты для каждой 
//пары прямоугольников, так что обход за квадрат.

function checkRectangles () {
	for (var i = 0; i < rectangles.length; i++) {
		var rect1 = getPosition(rectangles[i]);
		for (var j = i + 1; j < rectangles.length; j++) {
			var rect2 = getPosition(rectangles[j]);

			var res = rect1.left <= rect2.right && rect1.right >= rect2.left && rect1.top <= rect2.bottom && rect1.bottom >= rect2.top ;
				
			if (res) {
				return false;
			}
		}
	}
	return true;
}
//этой функцией мы проверяем победу, то есть истинно ли то, что прямоугольники не пересекаются
function checkVictory () {
	if (!checkRectangles()) {
		return;
	}
	
	var end = new Date();
//ставим счетчик, чтобы узнать время победы
	alert('You are victorious! Time elapsed: ' + ((end - startCounter) / 1000) + ' seconds.');
	active.className = "";
	active = null;
}
//получаем позицию прямоугольника

function getPosition (rectangle) {
	var result = {};
	result.left = parseInt(rectangle.style.left);
	result.top = parseInt(rectangle.style.top);
	result.right = result.left + parseInt(rectangle.style.width);
	result.bottom = result.top + parseInt(rectangle.style.height);

	return result;
}