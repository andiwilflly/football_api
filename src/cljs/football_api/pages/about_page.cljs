(ns football-api.pages.about
	(:require [reagent.core :as reagent :refer [atom]]))

(defn about-page []
	[:div [:h2 "About page..."]
	 [:div [:a {:href "/"} "go to home page"]]])
