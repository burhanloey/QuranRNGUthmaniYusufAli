(ql:quickload :drakma)
(ql:quickload :cl-json)

(defun json-request (url)
  (json:decode-json-from-string
   (flexi-streams:octets-to-string
    (drakma:http-request url))))

(defun get-json-value (alist indicator)
  (cdar
   (remove-if-not  ; filter
    (lambda (x) (equal (car x) indicator))
    alist)))

(defparameter *surahs*
  (json-request "http://api.alquran.cloud/surah"))

(defparameter *surahs-data*
  (get-json-value *surahs* :data))

(defparameter *ayahs*
  (mapcar
   (lambda (surah) (get-json-value surah :number-of-ayahs))
   *surahs-data*))

(defun print-number-of-ayahs ()
  (format t "~{~a~^, ~}" *ayahs*))

(defparameter *english-names*
  (mapcar
   (lambda (surah) (get-json-value surah :english-name))
   *surahs-data*))

(defun print-title-strings ()
  (format t "~:{<string name=\"title_~a\">~a</string>~%~}"
          (loop for i from 1 for name in *english-names*
                collect (list i name))))
