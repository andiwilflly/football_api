(ns football-api.pages.about_page
	(:require [reagent.core :as reagent :refer [atom]]))

(def log (.-log js/console))

(defn render []
	(log "@render About page")
	[:div [:h2 "About page..."]
	 [:div [:a {:href "/"} "go to home page"]]])
