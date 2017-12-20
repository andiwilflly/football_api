(ns football-api.models.test_model
	(:require
		[reagent.core :as reagent :refer [atom]]
		))

(def log (.-log js/console))


(def test_atom (atom 42))

(defn change_test_atom [value] (reset! test_atom value))
