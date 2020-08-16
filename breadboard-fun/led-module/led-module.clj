(require '[dali.io :as io])

(def ^:private grid-spacing 2.54)
(def ^:private group-spacing (* 3 grid-spacing))
(def ^:private row-spacing (* 1.5 grid-spacing))
(def ^:private column-spacing grid-spacing)

(defn- hole [x y]
  [:circle {:cx x 
            :cy y
            :r 0.5
            :fill :black}])

(defn- socket [y]
  (hole (* grid-spacing 1) y))

(defn- led [y]
  [:g (hole (* grid-spacing 3) y)
      (hole (* grid-spacing 4) y)])

(defn- resistor [row]
  [:g (hole (* grid-spacing 5) (* grid-spacing (inc row)))
      (hole (* grid-spacing 8) (* grid-spacing (inc row)))])

(defn- led-entourage [row]
  [:g (socket (* grid-spacing (inc row)))
      (led (* grid-spacing (inc row)))
      (resistor row)])

(defn- group-of-four [group-number]
  [:g 
   (led-entourage (* group-number group-spacing))
   (led-entourage (+ 1 (* group-number group-spacing)))
   (led-entourage (+ 2 (* group-number group-spacing)))
   (led-entourage (+ 3 (* group-number group-spacing)))])

(def led-module
  [:dali/page
   (group-of-four 0)
   (group-of-four 1)])

(io/render-svg led-module "led-module.svg")
