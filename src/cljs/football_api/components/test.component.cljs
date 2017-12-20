(ns football-api.components.test (
	:require
	    [football-api.models.test_model :as test_model]
	))

(def log (.-log js/console))

(defn render []
	(log "@render test_component")
	[:div "[@test_atom: " @test_model/test_atom "]"])
