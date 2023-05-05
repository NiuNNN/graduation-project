export function valueAllEmpty(object) {
  let isEmpty = true;
  Object.keys(object).forEach(function (x) {
    if (object[x] !== null && object[x] != '') {
      isEmpty = false;
    }
  });
  return isEmpty;
}
