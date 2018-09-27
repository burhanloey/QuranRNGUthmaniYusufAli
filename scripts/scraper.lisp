(ql:quickload :drakma)
(ql:quickload :cl-json)

(defparameter *result* (json:decode-json-from-string
                        (flexi-streams:octets-to-string
                         (drakma:http-request "http://api.alquran.cloud/surah"))))

(defun get-json-value (alist indicator)
  (car (remove-if-not (lambda (x)
                        (equal (car x) indicator))
                      alist)))

(defparameter *data* (cdr (get-json-value *result* :data)))

(defparameter *ayahs* (mapcar (lambda (x)
                                (cdr (get-json-value x :number-of-ayahs)))
                              *data*))

(defun print-number-of-ayahs ()
  (format t "~{~a~^, ~}" *ayahs*))
