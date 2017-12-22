(ns football-api.pages.home_page
	(:require
		[cljs.core.async :refer [<!]]
		[cljs-http.client :as http]
		[reagent.core :as reagent :refer [atom]]
		[football-api.components.test :as test_component]
		[football-api.models.test_model :as test_model]
		)
	(:require-macros [cljs.core.async.macros :refer [go]]))

(def log (.-log js/console))

(def test_atom_value (atom 3))

(defn test_atom_input []
	(log "@render test_atom_input")
	[:input {:type "text"
	         :value @test_atom_value
	         :on-change #(reset! test_atom_value (-> % .-target .-value))}])


(defn classtest [x y z]
	(reagent/create-class                 ;; <-- expects a map of functions
	 {:component-did-mount               ;; the name of a lifecycle function
	  #(println "component-did-mount")   ;; your implementation

	  :component-will-unmount            ;; the name of a lifecycle function
	  #(println "component-will-mUNount")  ;; your implementation

	  ;; other lifecycle funcs can go in here

	  :display-name  "my-component"  ;; for more helpful warnings & errors

	  :reagent-render        ;; Note:  is not :render
	  (fn [x y z]           ;; remember to repeat parameters
		  [:div (str x " +" y " " z)])}))


(defn- get_album []
	(go (let [response (<! (http/get "/album/atrist42/album45"))]
		    (prn  (:body response))
		    )))


(defn render []
	(log "@render home_page")
	[:div [:h2 "Welcome to " [:span {:style {:text-decoration "line-through"}} "football_api" ] " Last.fm search !" ]
	 [:div [:a {:href "/about"} "go to about page"]]
	 [test_atom_input]
	 [classtest 3 4 8]
	 [:button {:on-click get_album} "get_album!"]
	 [:button.test-class {:on-click #(test_model/change_test_atom @test_atom_value) } "Change @test_atom value"]
	 [test_component/render]])
