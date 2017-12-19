(ns football-api.pages.home
	(:require [reagent.core :as reagent :refer [atom]]))

(defn home-page []
	[:div [:h2 "Welcome to " [:span {:style {:text-decoration "line-through"}} "football_api" ] " Last.fm search !" ]
	 [:div [:a {:href "/about"} "go to about page"]]])
