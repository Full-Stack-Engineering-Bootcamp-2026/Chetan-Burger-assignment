function BillDisplay({ orderData }) {
  if (!orderData) return null;

  return (
    <div className="bill-display space-y-6">
      <div className="rounded-3xl border border-slate-200 bg-slate-50 p-6">
        <h2 className="text-2xl font-semibold text-slate-900">Order Confirmation</h2>
        <p className="mt-2 text-sm text-slate-600">Order : { orderData.id}</p>
        <div className="mt-4 grid gap-3 sm:grid-cols-3">
          <div className="rounded-3xl bg-white p-4 shadow-sm">
            <p className="mt-2 text-sm bold font-semibold text-slate-900">name : {orderData.userName}</p>
            <p className="text-sm text-slate-500">email : {orderData.email}</p>
          </div>
          <div className="rounded-3xl bg-white p-4 shadow-sm">
            <p className="text-xs uppercase tracking-[0.2em] text-slate-500">Optimized Total</p>
            <p className="mt-2 text-2xl font-semibold text-slate-900">₹{orderData.optimizedTotal}</p>
          </div>
          <div className="rounded-3xl bg-white p-4 shadow-sm">
            <p className="text-xs uppercase tracking-[0.2em] text-slate-500">You Saved</p>
            <p className="mt-2 text-2xl font-semibold text-emerald-700">₹{orderData.savings || (orderData.actualTotal - orderData.optimizedTotal)}</p>
          </div>
        </div>
      </div>

      <div className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">
        <h3 className="text-xl font-semibold text-slate-900">Items Ordered</h3>
        <div className="mt-4 overflow-x-auto">
          <table className="w-full text-left text-sm text-slate-700">
            <thead className="border-b border-slate-200 text-slate-900">
              <tr>
                <th className="px-4 py-3">Product</th>
                <th className="px-4 py-3">Unit Price</th>
                <th className="px-4 py-3">Qty</th>
                <th className="px-4 py-3">Total</th>
              </tr>
            </thead>
            <tbody>
              {orderData.items.map((item, index) => (
                <tr key={index} className="border-b border-slate-100">
                  <td className="px-4 py-3">{item.productName}</td>
                  <td className="px-4 py-3">₹{item.unitPrice}</td>
                  <td className="px-4 py-3">{item.quantity}</td>
                  <td className="px-4 py-3">₹{item.totalPrice}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {orderData.appliedCombos && orderData.appliedCombos.length > 0 && (
        <div className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">
          <h3 className="text-xl font-semibold text-slate-900">Combos Applied</h3>
          <ul className="mt-4 space-y-3 text-sm text-slate-700">
            {orderData.appliedCombos.map((combo, index) => (
              <li key={index} className="rounded-3xl border border-slate-100 bg-slate-50 p-4">
                <div className="flex flex-col gap-2 sm:flex-row sm:items-center sm:justify-between">
                  <div>
                    <p className="font-semibold text-slate-900">{combo.comboName}</p>
                    <p className="text-slate-500">Applied {combo.timesApplied} time{combo.timesApplied === 1 ? '' : 's'}</p>
                  </div>
                  <p className="text-sm font-semibold text-slate-900">₹{combo.comboPrice}</p>
                </div>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}

export default BillDisplay;
